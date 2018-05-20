package ServerImpl;
import JavaBean.Entity.Mail;
import JavaBean.Entity.User;
import JavaBean.Dao.MailDao;
import JavaBean.Dao.UserDao;
import JavaImpl.MailImpl;
import JavaImpl.UserImpl;

import java.io.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class POP3Server extends Thread {

    private BufferedReader buffread;
    private OutputStream outs;
    private InputStream ins;
    private User user=new User();
    private boolean flag=true;
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
    String str;
    List<Mail> mail ;
    int length;
    int [] mailLength;

    private void processMessage(Socket client) throws Exception {
        ins = client.getInputStream();
        outs= client.getOutputStream();
        DataInputStream dins = new DataInputStream(ins);

//        ObjectInputStream ois = new ObjectInputStream(ins);
        ObjectOutputStream oos = new ObjectOutputStream(outs);
//        sendMsgToMe("+OK Welcome to POP3Server Mail Server\r\n");

        ExecutorService mThreadPool = Executors.newCachedThreadPool();
        MailDao mailDao = new MailImpl();
        user = new User();
        user.setUsr_id("dbg@bro.com");

        mail = mailDao.getMails(user);
        length=mail.size();
        mailLength=new int[length];
        for(int i=0;i<length;i++) {
            mailLength[i]=mail.get(i).getContent().length();
        }
        while (flag) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        buffread = new BufferedReader(new InputStreamReader(ins));
                        str = buffread.readLine().toLowerCase();
                        System.out.println(str);

                        if(str.equals("quit")){
                            sendMsgToMe("+OK quit successfully");
                            flag = false;
                        }
                        else if(str.equals("list")){
                            System.out.println("hhh");
                            System.out.println(mail.size());
//                                sendMsgToMe("aaaaa");
                            oos.writeObject(mail);
                            oos.flush();
                        }
                        else if(str.equals("sent")){
                            mail = mailDao.getSentOrDraftMails(user,0);
                            oos.writeObject(mail);
                            oos.flush();
                        }
                        else if(str.equals("draft")){
                            mail = mailDao.getSentOrDraftMails(user,1);
                            oos.writeObject(mail);
                            oos.flush();
                        }
                        else if(str.contains("retr")){
                            if(str.equals("retr")){
                                sendMsgToMe("-ERR retr");
                            }else {
                                int n=Integer.parseInt(str.substring(5));
//                        sendMsgToMe("mail_id:"+mail.get(n).getMail_id());
//                        sendMsgToMe("From:"+mail.get(n).getFrom());
//                        sendMsgToMe("To:"+mail.get(n).getTo());
//                        sendMsgToMe("Subject:"+mail.get(n).getSubject());
//                        sendMsgToMe("content:"+mail.get(n).getContent());
//                        sendMsgToMe("time:"+mail.get(n).getTime());
                                Mail mail1 = mailDao.getMail(n);
                                oos.writeObject(mail1);
                            }
                        }
                        else if(str.contains("dele")){
                            if(str.equals("dele")){
                                sendMsgToMe("-ERR dele");
                            }
                            else{
                                int n=Integer.parseInt(str.substring(5));
                                mailDao.deleMail(mail.get(n).getMail_id());
                                mail = mailDao.getMails(user);
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    private boolean welcomeAndLogin(User user) {
        UserDao userDao = new UserImpl();
        user = userDao.login(user.getUsr_id(), user.getPassword());
        if (user == null) {
            user =new User();
            sendMsgToMe("-ERR user");
            flag=false;
            return false;
        } else {
            System.out.println(user.getUsrname());
            return true;
        }
    }

    public void run (){
        try{
            processMessage(this.client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
