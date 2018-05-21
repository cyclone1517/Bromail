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

    private int port;

    public enum ServerType {POP, SMTP}

    private ServerType serverType;

    private boolean run_state = false;

    private ServerSocket mailServer;

    public ServiceManageImpl(int port, ServerType serverType){
        this.port = port;
        this.serverType = serverType;
    }

    @Override
    public boolean startPOP3(int port) {
        try {
            mailServer = new ServerSocket(port);
            run_state = true;
            System.out.println("POPServer has been set up.");
            while(run_state){
                Socket client = mailServer.accept();
                POP3Server ps = new POP3Server(client);
                ps.start();
                System.out.println("a new thread has been started to process a new client");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean stopPOP3() {
        this.run_state = false;
        try{
            mailServer.close();
        }catch(Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean startSMTP(int port) {
        try {
            mailServer = new ServerSocket(port);
            run_state = true;
            System.out.println("SMTPServer has been set up.");
            while(run_state){
                Socket client = mailServer.accept();
                SMTPServer ss = new SMTPServer(client);
                ss.start();
                System.out.println("a new thread has been started to process a new client");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean stopSMTP() {
        this.run_state = false;
        try{
            mailServer.close();
        }catch(Exception e){
            return false;
        }
        return true;
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

    public boolean isRun_state() {
        return this.run_state;
    }
}