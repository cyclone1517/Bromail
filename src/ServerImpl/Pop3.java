package ServerImpl;
import JavaBean.Mail;
import JavaBean.User;
import JavaDao.MailDao;
import JavaDao.UserDao;
import JavaImpl.MailImpl;
import JavaImpl.UserImpl;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Pop3 {

    private BufferedReader buffread;
    private OutputStream outs;
    private InputStream ins;
    private User user;
    public static void main(String[] args) {
        try {
            Pop3 server = new Pop3();
            server.setupPop3Sever(110);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setupPop3Sever(int port) throws IOException {
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket client = server.accept();
//                System.out.println("客户端IP：" + client.getRemoteSocketAddress());
                processMesage(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void processMesage(Socket client) throws IOException {
        ins = client.getInputStream();
        outs=client.getOutputStream();
        buffread = new BufferedReader(new InputStreamReader(ins));
        if (!welcomeAndLogin()){
            client.close();
        }
        else {
            sendMsgToMe("\r\nYou have been logged in successfully!\r\n");
            List<Mail> mail = new ArrayList<Mail>();
            MailDao mailDao = new MailImpl();
            mail = mailDao.getMail(user);

            for (int i = 0; i < mail.size(); i++) {
                sendMsgToMe("\r250 " + mail.get(i).getMail_id() + "... sender OK\n");
                sendMsgToMe("\r250 " + mail.get(i).getFrom() + "... sender OK\n");
                sendMsgToMe("\r250 " + mail.get(i).getSubject() + "... sender OK\n");
                sendMsgToMe("\r250 " + mail.get(i).getContent() + "... sender OK\n");
                sendMsgToMe("\r250 "+mail.get(i).getTime()+"... sender OK\n");
            }
            client.close();
        }
//        DataInputStream dins = new DataInputStream(ins);
//        //服务端解包过程
//        while (true) {
//            int totalLen = dins.readInt();
//            byte flag = dins.readByte();
//            System.out.println("接收消息类型" + flag);
//
//            byte[] data = new byte[totalLen - 4 - 1];
//            dins.readFully(data);
//            String msg = new String(data);
//            System.out.println("发来的内容是:" + msg);
//        }

    }


    private boolean welcomeAndLogin(){
        try {
            sendMsgToMe("+OK Welcome to pretended brothers' Mail Pop3 Server!\r\n");
            sendMsgToMe("please enter your userId: ");

            String userId = buffread.readLine();
            sendMsgToMe("\r\nplease enter your password: ");
            String password = buffread.readLine();

            UserDao userDao = new UserImpl();
            user = userDao.login(userId, password);
            if(user==null){
                sendMsgToMe("\r\nsorry no such user exists");
                return false;
            }
            else{
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void sendMsgToMe(String msg){
        byte[] data = msg.getBytes();
        try {
            outs.write(data);
            outs.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
