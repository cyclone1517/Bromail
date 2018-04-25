package ServerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ServerInterface.ServiceManage;

/**
 *
 * @author YukonChen
 * 多线程服务器
 *
 */

public class ServiceManageImpl extends Thread implements ServiceManage{
    // 服务器的端口号
    private int port;
    enum ServerType {POP, SMTP}
    private ServerType serverType;

    // 服务器的运行状态
    private boolean run_state = false;

    // 服务器实体
    private ServerSocket mailServer;
    //private SMTPSever smtpSever;
//	初始是private
    public ServiceManageImpl(int port, ServerType serverType){
        this.port = port;
        this.serverType = serverType;
    }

    public ServiceManageImpl(){}

    @Override
    public boolean stopPOP3() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean startPOP3(int port) {
        try {
            // 实例化对象
            mailServer = new ServerSocket(port);
            // 服务器运行状态变为true
            run_state = true;
            System.out.println("POPServer has been set up.");
            while(run_state){
                Socket client = mailServer.accept();
                POP3Server std = new POP3Server(client);
                std.start();
                System.out.println("a new thread has been started to process a new client");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean startServer(int port) {
        return false;
    }

    @Override
    public boolean startServer() {
        return false;
    }

    @Override
    public boolean stopServer() {
        return false;
    }

    @Override
    public boolean startSMTP(int port) {
        try {
            // 实例化对象
            mailServer = new ServerSocket(port);
            // 服务器运行状态变为true
            run_state = true;
            System.out.println("SMTPServer has been set up.");
            while(run_state){
                Socket client = mailServer.accept();
                SMTPServer std = new SMTPServer(client);
                std.start();
                System.out.println("a new thread has been started to process a new client");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isRun_state() {
        return this.run_state;
    }

    // by bw97
    @Override
    public boolean stopSMTP() {
        this.run_state = false;
        try{
            mailServer.close();
        }catch(Exception ef){
            return false;
        }
        return true;
    }

    public static void main(String args[]){
		ServiceManageImpl svcManage = new ServiceManageImpl();
		svcManage.startSMTP(9090);
    }

    @Override
    public void run() {
        if(serverType == ServerType.SMTP){
            startSMTP(port);
        }
        if(serverType == ServerType.POP){
            startPOP3(port);
        }
    }
}