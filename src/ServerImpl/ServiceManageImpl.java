package ServerImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import ServerInterface.ServiceManage;

import javax.swing.*;

/**
 * 
 * @author YukonChen
 * 多线程服务器
 *
 */

public class ServiceManageImpl extends Thread implements ServiceManage{

	private int port;

//	初始是private
	public ServiceManageImpl(int port){
		this.port = port;
	}

	public ServiceManageImpl(){}

	@Override
	public boolean stopSMTP() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startSMTP() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopPOP3() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startPOP3() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startServer(int port) {
		return false;
	}

	@Override
	public boolean startServer() {
		//打开服务器只能私有调用 test whether Chinese could be wrong
		try {
			java.net.ServerSocket mailServer = new java.net.ServerSocket(this.port);
			System.out.println("Server has been set up.");
			while(true){
				java.net.Socket client = mailServer.accept();
				ServerThread std = new ServerThread(client);
				std.start();
				System.out.println("a new thread has been started to process a new client");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean stopServer() {
		// TODO Auto-generated method stub
		return false;
	}

	public static void main(String args[]){
//		ServiceManageImpl svcManage = new ServiceManageImpl();
//		svcManage.startServer(9091);
	}

	@Override
	public void run() {
		startServer();
	}
}