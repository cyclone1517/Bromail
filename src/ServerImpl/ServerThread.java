package ServerImpl;

import JavaBean.Mail;
import JavaBean.User;
import JavaDao.MailDao;
import JavaDao.UserDao;
import JavaImpl.MailImpl;
import JavaImpl.UserImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Date;
import java.sql.Timestamp;

public class ServerThread extends Thread{

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
	public ServerThread(java.net.Socket client) {
		this.client = client;
	}
	
	public void sendMsgToMe(String msg){
		byte[] data = msg.getBytes();
		try {
			System.out.println("[ServerThread]data is:" + data);
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

	private void processChat(Socket client){
		try {
			ops = client.getOutputStream();
			ips = client.getInputStream();
			buffread = new BufferedReader(new InputStreamReader(ips));

			if (!welcomeAndLogin()){
				client.close();
			}
			sendMsgToMe("\r\nYou have been logged in successfully!\r\n");

			Mail mail = new Mail();
			int state = 0;
			StringBuilder stringBuilder = new StringBuilder();
			String str = "";
			while (flag) {
				str = buffread.readLine();
				if(str.contains("MAIL FROM:")) {
					String sender = str.substring(str.indexOf('<')+1, str.lastIndexOf('>'));
					mail.setFrom(sender);
					sendMsgToMe("250 "+sender+"... sender OK\n");
				}
				else if (str.contains("RCPT TO:")) {
					String receiver = str.substring(str.indexOf('<')+1, str.lastIndexOf('>'));
					mail.setTo(receiver);
					sendMsgToMe("250 "+receiver+"... receiver OK\n");
				}
				else if (str.equals("SUBJ")) {
					sendMsgToMe("350 Enter Subject. end with \\n \n");
					state=1;
					continue;
				}
				else if (str.equals("DATA")) {
					sendMsgToMe("354 Enter mail, end with \".\" on a line by itself\n");
					state=2;
					continue;
				}
				else if (str.equals("QUIT")) {
					break;
				}
				else {
					sendMsgToMe("Invalid Command!\n");
					continue;
				}
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
				sendMsgToMe("\r\nsorry no such user exists");
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
		processChat(this.client);
	}

}
