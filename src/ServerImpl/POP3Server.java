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

public class POP3Server extends Thread {

    private BufferedReader buffread;
    private OutputStream outs;
    private InputStream ins;
    private User user=new User();
    private int flag=0;
    Socket client;

    public POP3Server(java.net.Socket client) {
        this.client = client;
    }

    private void sendMsgToMe(String msg){
        msg=msg+"\r\n";
        byte[] data = msg.getBytes();
        try {
            outs.write(data);
            outs.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void stopServer() {
//        try {
//            client.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }

//    public void setupPop3Sever(int port) throws IOException {
//        try {
//            ServerSocket server = new ServerSocket(port);
//            while (true) {
//                client = server.accept();
//                System.out.println("客户端IP：" + client.getRemoteSocketAddress());
//                processMesage(client);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    private void processMesage(Socket client) throws IOException {
        ins = client.getInputStream();
        outs= client.getOutputStream();
        buffread = new BufferedReader(new InputStreamReader(ins));
        sendMsgToMe("+OK Welcome to POP3Server Mail Server\r\n");
        while(true){
            while(true){
                String str=buffread.readLine().toLowerCase();
                if(str.equals("quit")){
                    flag=0;
                    break;
                }
                else if(str.contains("user")){
                    if(str.equals("user")){
                        sendMsgToMe("-ERR Unknown command user");
                    }
                    else {
                        System.out.print(str.substring(5));
                        user.setUsr_id(str.substring(5));
                        flag=1;
                    }
                }
                else if(str.contains("pass")){
                    if(str.equals("pass")){
                        sendMsgToMe("-ERR Unknown command pass");
                    }
                    else{
                        user.setPassword(str.substring(5));
                        System.out.print(str.substring(5));
                        if(flag==1){
                            if(welcomeAndLogin()){
                                sendMsgToMe("+OK login successfully");
                                break;
                            }
                            else{
                                sendMsgToMe("-ERR");
                            }
                        }
                        else {
                            sendMsgToMe("-ERR");
                        }
                    }
                }
                else {
                    sendMsgToMe("-ERR");
                }
            }
            List<Mail> mail = new ArrayList<Mail>();
            MailDao mailDao = new MailImpl();
            mail = mailDao.getMail(user);
            int length=mail.size();
            int [] mailLength=new int[length];
            for(int i=0;i<length;i++) {
                mailLength[i]=mail.get(i).getContent().length();
            }

            while(flag==1) {
                String str=buffread.readLine().toLowerCase();
                if(str.equals("quit")){
                    sendMsgToMe("+OK quit successfully");
                    break;
                }
                else if(str.equals("list")){
                    for(int i=0;i<length;i++){
                        sendMsgToMe(i+" "+mailLength[i]);
                    }
                    sendMsgToMe(".");
                }
                else if(str.contains("retr")){
                    if(str.equals("retr")){
                        sendMsgToMe("-ERR retr");
                    }else {
                        int n=Integer.parseInt(str.substring(5));
                        sendMsgToMe("mail_id:"+mail.get(n).getMail_id());
                        sendMsgToMe("From:"+mail.get(n).getFrom());
                        sendMsgToMe("To:"+mail.get(n).getTo());
                        sendMsgToMe("Subject:"+mail.get(n).getSubject());
                        sendMsgToMe("content:"+mail.get(n).getContent());
                        sendMsgToMe("time:"+mail.get(n).getTime());
                    }
                }
                else if(str.contains("dele")){
                    if(str.equals("dele")){
                        sendMsgToMe("-ERR dele");
                    }
                    else{
                        int n=Integer.parseInt(str.substring(5));
                        mailDao.deleMail(mail.get(n).getMail_id());
                        mail = mailDao.getMail(user);
                        sendMsgToMe("+OK delete successfully");
                        length=mail.size();
                        mailLength=new int[length];
                        for(int i=0;i<length;i++) {
                            mailLength[i]=mail.get(i).getContent().length();
                        }
                    }
                }
                else {
                    sendMsgToMe("-ERR");
                }
            }
            if(flag==0){
                client.close();
                break;
            }
        }


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


    private boolean welcomeAndLogin() {
        UserDao userDao = new UserImpl();
        user = userDao.login(user.getUsr_id(), user.getPassword());
        if (user == null) {
            user =new User();
            sendMsgToMe("-ERR user");
            flag=0;
            return false;
        } else {
            System.out.println(user.getUsrname());
            return true;
        }
    }



    public void run (){
        try{
            processMesage(this.client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
