package ServerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SMTPSever {

    public void setupSMTPSever(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("SMTP Sever Activated! Port is "+port);
            while (true) {
                Socket client = server.accept();
                System.out.println("Incoming client: "+ client.getRemoteSocketAddress());
                ServerThread serverThread = new ServerThread(client);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SMTPSever sever = new SMTPSever();
        sever.setupSMTPSever(9090);
    }


}
