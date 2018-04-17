package ServerImpl;

import java.io.IOException;

import ServerInterface.ServiceManage;

/**
 * 
 * @author YukonChen
 * 多线程服务器
 *
 */

public class ServiceManageImpl implements ServiceManage{
	
	private ServiceManageImpl(){};

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
		//打开服务器只能私有调用 test whether Chinese could be wrong
		try {
			java.net.ServerSocket mailServer = new java.net.ServerSocket(port);
			System.out.println("Server has been set up.");
			while(true){
				java.net.Socket client = mailServer.accept();
//				ServerThread std = new ServerThread(client);
//				std.start();
				System.out.println("a new thread has been started to process a new client");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean stopServer() {
		// TODO Auto-generated method stub
		return false;
	}
	
//	public static void main(String args[]){
//		ServiceManage svcManage = new ServiceManageImpl();
//		svcManage.startServer(9091);
//	}
	
}