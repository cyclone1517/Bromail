package ServerImpl;

import java.io.IOException;
import java.io.OutputStream;

public class ServerThread extends Thread{

	private java.net.Socket client;
	private OutputStream ops;
	
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
			
			sendMsgToMe("Welcome to pretended brothers' mailServer!\r\n");

			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public void run(){
		processChat(this.client);
	}

}
