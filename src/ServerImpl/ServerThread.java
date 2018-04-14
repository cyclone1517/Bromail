package ServerImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ServerThread extends Thread{

	private java.net.Socket client;
	private OutputStream ops;
//	private UserInfo user;
//	
//	public UserInfo getOwnUser(){
//		return user;
//	}
	
	public ServerThread(java.net.Socket client) {
		this.client = client;
	}
	
	public void sendMsgToMe(String msg){
		byte[] data = msg.getBytes();
		//System.out.println("[ServerThread]sending..." + msg + "\r\n");
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
//			InputStream ins = client.getInputStream();
//			BufferedReader brd = new BufferedReader(new InputStreamReader(ins));
			
			sendMsgToMe("Welcome to pretended brothers' mailServer!\r\n");
//			sendMsg2Me("Please enter your username!\r\n");
//			String userName = brd.readLine();
//			sendMsg2Me("Please enter your psd!\r\n");
//			String pwd = brd.readLine();
			
//			user = new UserInfo();
//			user.setName(userName);
//			user.setPsd(pwd);
//			
//			boolean loginState = DaoTools.checkLogin(user);
//			if(!loginState){
//				this.closeMe();
//				return;
//			}
//			ChatTools.addClient(this);
//			
//			String s = "hello, welcome to cyl's server! \r\n";
//			sendMsg2Me(s);
//			String inputS = readString(ins);
			
//			while(!inputS.equals("bye")){
//				System.out.println("[ServerThread]The server got:"+inputS);
//				ChatTools.castMsg(user,inputS);
//				inputS = readString(ins);
//			}
//			
//			s = "welcome to my server again!";
//			this.sendMsg2Me(s);
//			closeMe();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
//	public String readString(InputStream ins) throws IOException {
//		StringBuffer stb = new StringBuffer();
//		char c = 0;
//		while(c!=13){
//			int i = ins.read();
//			c = (char)i;
//			stb.append(c);
//		}
//		//String inputS = stb.toString();
//		String inputS = stb.toString().trim();
//		return inputS;
//	}	
	
//	public void closeMe(){
//		try {
//			client.close();
//			ChatTools.removeClient(user);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void run(){
		processChat(this.client);
	}

}
