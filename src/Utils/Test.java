package Utils;

import ServerImpl.ServiceManageImpl;

public class Test {
    public static ServiceManageImpl SMTP_Server = null;
    public static ServiceManageImpl POP3_server = null;
    private static int smtp_port = 9090;
    private static int pop3_port = 9091;

    public static void main(String args[]) {
        if (SMTP_Server == null){

            SMTP_Server = new ServiceManageImpl(smtp_port, ServiceManageImpl.ServerType.SMTP);
            SMTP_Server.start();

            POP3_server = new ServiceManageImpl(pop3_port, ServiceManageImpl.ServerType.POP);
            POP3_server.start();

        }else if (SMTP_Server.isRun_state()){

            SMTP_Server.stopSMTP();
            SMTP_Server = null;

            POP3_server.stopPOP3();
            POP3_server = null;

        }
    }

    /**
     *  对于扩增功能的说明：
     *  SMTP协议：
     *  POP协议：
     *      登陆验证
     */

}
