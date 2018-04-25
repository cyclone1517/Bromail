package ServerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SMTPServer {
    private boolean state = false;
    ServerSocket server;

    public void setupServer(int port) throws IOException {
        this.state = true;

        server = new ServerSocket(port);
        System.out.println("SMTP Sever Activated! Port is "+port);
        while (state) {
            Socket client = server.accept();
            System.out.println("Incoming client: "+ client.getRemoteSocketAddress());
            ServerThread serverThread = new ServerThread(client);
            serverThread.start();
        }
    }
    public void stopServer() throws IOException {
        this.state = false;
        server.close();
    }
    public static void main(String[] args) {
        SMTPServer server = new SMTPServer();
        try {
            server.setupServer(9090);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
