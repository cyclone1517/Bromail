package ServerImpl;

import JavaBean.Mail;
import JavaBean.User;
import JavaDao.UserDao;
import JavaImpl.UserImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread{

	private java.net.Socket client;
	private OutputStream ops;
	private InputStream ips;
	private BufferedReader buffread;
	private String from;
	private String to;
	private String subject;
	private String content;

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
	
	public void processChat(Socket client){
		try {
			ops = client.getOutputStream();
			ips = client.getInputStream();
			buffread = new BufferedReader(new InputStreamReader(ips));

			if (!welcomeAndLogin()){
				client.close();
			}
			sendMsgToMe("\r\nYou have been logged in successfully!\r\n");
			String str = buffread.readLine();
			Mail mail = new Mail();
			int flag = 0;
			while (!"quit".equals(str)) {
				if(str.contains("MAIL FROM:")) {
					String sender = str.substring(str.indexOf('<'), str.lastIndexOf('>'));
					mail.setFrom(sender);
					sendMsgToMe("250 "+sender+"... sender OK");
				}
				else if (str.contains("RCPT TO:")) {
					String receiver = str.substring(str.indexOf('<'), str.lastIndexOf('>'));
					mail.setTo(receiver);
					sendMsgToMe("250 "+receiver+"... receiver OK");
				}
				else if (str.equals("SUBJ")) {
					sendMsgToMe("350 Enter Subject. end with \\n");
					flag=1;
				}
				else if (str.equals("DATA")) {
					sendMsgToMe("354 Enter mail, end with \".\" on a line by itself");
					flag=2;
				}

				if (flag==1) {
					mail.setSubject(str);
				}
				else if (flag==2) {
					StringBuffer stringBuffer = new StringBuffer();
					if (str.equals(".")) {
						mail.setContent(stringBuffer.toString());
					}
					stringBuffer.append(str);

				}

				str = buffread.readLine();
			}
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
