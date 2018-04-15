package ServerImpl;

import JavaBean.User;
import JavaDao.UserDao;
import JavaImpl.UserImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerThread extends Thread{

	private java.net.Socket client;
	private OutputStream ops;
	private InputStream ips;
	private BufferedReader buffread;
	
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
	
	public void processChat(java.net.Socket client){
		try {
			ops = client.getOutputStream();
			ips = client.getInputStream();
			buffread = new BufferedReader(new InputStreamReader(ips));

			if (!welcomeAndLogin()){
				client.close();
			}
			sendMsgToMe("\r\nYou have been logged in successfully!\r\n");
			
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
