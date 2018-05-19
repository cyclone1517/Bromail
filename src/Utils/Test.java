package Utils;

import ServerImpl.ServiceManageImpl;

public class Test {
    public static ServiceManageImpl myServer = null;
    private static int smtp_port = 9090;
    private static int pop3_port = 9091;

    public static void main(String args[]) {
        if (myServer == null){
            myServer = new ServiceManageImpl(smtp_port, ServiceManageImpl.ServerType.SMTP);
            myServer.start();
            ServiceManageImpl sm = new ServiceManageImpl(pop3_port, ServiceManageImpl.ServerType.POP);
            sm.start();
        }else if (myServer.isRun_state()){
            myServer.stopServer();
            myServer = null;
        }
    }

    /**
     *  对于扩增功能的说明：
     *  SMTP协议：
     *  POP协议：
     *      登陆验证
     */

}
