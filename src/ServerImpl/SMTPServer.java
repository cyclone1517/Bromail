package ServerImpl;

import JavaBean.Mail;
import JavaBean.User;
import JavaBean.FriendInfo;
import JavaDao.LogDao;
import JavaDao.MailDao;
import JavaDao.UserDao;
import JavaDao.FriendDao;
import JavaImpl.MailImpl;
import JavaImpl.UserImpl;
import JavaImpl.FriendImpl;
import ServerInterface.LogManage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.List;

public class SMTPServer extends Thread{

	private java.net.Socket client;
	private OutputStream ops;
	private InputStream ips;
	private BufferedReader buffread;
	private String from;
	private String to;
	private String subject;
	private String content;
	private boolean flag;
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public SMTPServer(java.net.Socket client) {
		this.client = client;
	}

	public void sendMsgToMe(String msg){
		byte[] data = msg.getBytes();
		try {
			System.out.println("[SMTPServer]data is:" + data);
			ops.write(data);
			ops.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private boolean state = false;
	ServerSocket server;


	public void stopServer() throws IOException {
		this.state = false;
		server.close();
	}

	private String[] commands = {"helo", "auth", "mail", "rcpt", "data", "quit"};

	public void handleInput(SMTPServer server, String inStr) {

		if (!checkCommand(inStr)) {
			server.sendMsgToMe("500 Invalid command");
			return;
		}
		String com = this.getCommand(inStr);
		String arg = this.getArgument(inStr);

		if ("quit".equals(com)) {
			server.setFlag(false);
			return;
		}
	}
	private String getCommand(String inStr) {
		int bPos = inStr.indexOf(" ");
		if (bPos == -1)
			return inStr.toLowerCase();
		return inStr.substring(0, bPos).toLowerCase();
	}
	private String getArgument(String inStr) {
		int bPos = inStr.indexOf(" ");
		if (bPos == -1)
			return "";
		return inStr.substring(bPos + 1, inStr.length());
	}
	private boolean checkCommand(String inStr) {
		if ("".equals(inStr))
			return false;

		String com = getCommand(inStr);

		for (int i = 0; i < commands.length; i++)
			if (commands[i].endsWith(com))
				return true;
		return false;
	}
	private void processChat(Socket client){
		String servername = "bro.com";
		try {
			ops = client.getOutputStream();
			ips = client.getInputStream();
			buffread = new BufferedReader(new InputStreamReader(ips));

			if (!welcomeAndLogin()){
				client.close();
			}
			sendMsgToMe("\r\nYou have been logged in successfully!\r\n");
			sendMsgToMe("220 bro.com\n");

			Mail mail = new Mail();
			int state = 0;
			StringBuilder stringBuilder = new StringBuilder();
			String str = "";
			flag = true;
			while (flag) {
				str = buffread.readLine();
				if (str.toUpperCase().startsWith("HELO")) {
					String clientname = str.substring(str.indexOf(" ")+1, str.length());
					sendMsgToMe("250 Hello "+clientname+", Please to meet you\n");
				}
				else if(str.toUpperCase().contains("MAIL FROM:")) {
					String sender = str.substring(str.indexOf('<')+1, str.lastIndexOf('>'));
					mail.setFrom(sender);
					sendMsgToMe("250 "+sender+"... sender OK\n");
				}
				else if (str.toUpperCase().contains("RCPT TO:")) {
					String receiver = str.substring(str.indexOf('<')+1, str.lastIndexOf('>'));
					mail.setTo(receiver);
					sendMsgToMe("250 "+receiver+"... receiver OK\n");
				}
				else if (str.toUpperCase().equals("SUBJ")) {
					sendMsgToMe("350 Enter Subject. end with \\n \n");
					state=1;
					continue;
				}
				else if (str.toUpperCase().equals("DATA")) {
					sendMsgToMe("354 Enter mail, end with \".\" on a line by itself\n");
					state=2;
					continue;
				}
				else if (str.toUpperCase().equals("QUIT")) {
					sendMsgToMe("221 "+servername+"\n");
					break;
				}
				/**
				 * @author: YukonChen
				 * 自定义朋友搜索指令
				 */
				else if (str.equals("FRND")){
					FriendDao fd = new FriendImpl();
					List<FriendInfo> friendInfoList = fd.searchFriend("1000", "200");
					int len = (friendInfoList==null)? 0:friendInfoList.size();
					if(len==0){
						sendMsgToMe("Sorry, you have not added any friends\r\n");
					}
					for(int ind=0; ind<len; ind++){
						FriendInfo friendInfo = friendInfoList.get(ind);
						sendMsgToMe("#" + ind + ": " + friendInfo.getkeywordRst() + "\r\n");
					}
				}
//				else {
//					sendMsgToMe("500 Invalid Command!\n");
//					continue;
//				}
				if (state==1) {
					mail.setSubject(str);
					sendMsgToMe("Subject OK\n");
				}
				else if (state==2) {
					if (str.equals(".")) {
						mail.setContent(stringBuilder.toString());
						sendMsgToMe("Content OK\n");
					}else {
						stringBuilder.append(str);
					}
				}

			}
			Timestamp time = new Timestamp(new java.util.Date().getTime());
			mail.setTime(time);
			MailDao mailDao = new MailImpl();
			mailDao.storeMail(mail);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void receiveMail(Mail mail) {
		MailDao mailDao = new MailImpl();
		mailDao.storeMail(mail);
	}
	private boolean welcomeAndLogin(){
		try {
			sendMsgToMe("Welcome to pretended brothers' mailServer!\r\n");
			sendMsgToMe("please enter your userId: ");
			String userId = buffread.readLine();
			sendMsgToMe("\r\nplease enter your password: ");
			String password = buffread.readLine();

			UserDao userDao = new UserImpl();
			User user = userDao.login(userId, password);
			if(user==null){
				sendMsgToMe("\r\nsorry no such user exists\r\n");
				return false;
			}
			else{
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void run(){
		LogManage logManage = new LogManageImpl();
		logManage.addLog(LogDao.LogType.SMTP, client);
		System.out.println("Incoming client:" + client.getRemoteSocketAddress());
		processChat(this.client);
	}

}
