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
    ObjectInputStream ois;
    ObjectOutputStream oos;
    private User user = new User();
    private boolean flag=true;
    Socket client;

    public POP3Server(java.net.Socket client) {
        this.client = client;
        try {
            ins = client.getInputStream();
            outs = client.getOutputStream();
            ois = new ObjectInputStream(ins);
            oos = new ObjectOutputStream(outs);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendDataToClient(Object obj){
        try {
            oos.writeObject(obj);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String str;
    List<Mail> mail ;
    int length;
    int [] mailLength;
    String err = "ERROR";
    private  ExecutorService mThreadPool ;
    private void processMessage(Socket client) throws Exception {

        DataInputStream dins = new DataInputStream(ins);

        MailDao mailDao = new MailImpl();
        user = new User();
        user.setUsr_id("dbg@bro.com");
        user.setPassword("123456");
        mail = mailDao.getMails(user);
        length = mail.size();
        mailLength = new int[length];
        for (int i = 0; i < length; i++) {
            mailLength[i] = mail.get(i).getContent().length();
        }

        while (flag) {
            str = (String) ois.readObject();
            System.out.println(str);
            try {
                if (str.equals("quit")) {
                    flag = false;
                    sendDataToClient("Bye Bye");
                    oos.close();
                    System.out.println("process terminated");
                } else if (str.equals("list")) {
                    System.out.println("get all mails");
                    System.out.println(mail.size());
                    sendDataToClient(mail);
                } else if (str.equals("sent")) {
                    mail = mailDao.getSentOrDraftMails(user, 0);
                    System.out.println("get sent mails");
                    System.out.println(mail.size());
                    sendDataToClient(mail);
                } else if (str.equals("draft")) {
                    mail = mailDao.getSentOrDraftMails(user, 1);
                    System.out.println("get draft mails");
                    System.out.println(mail.size());
                    sendDataToClient(mail);
                } else if (str.contains("dele")) {
                    if (str.equals("dele")) {
                        sendDataToClient(err);
                    } else {
                        int mail_id = Integer.parseInt(str.substring(5));
                        boolean res = mailDao.deleMail(mail_id);
                        mail = mailDao.getMails(user);
                        System.out.println("deleted successfully");
                        System.out.println(mail.size());
                        sendDataToClient(mail);
                        sendDataToClient(res);
                        length = mail.size();
                        mailLength = new int[length];
                        for (int i = 0; i < length; i++) {
                            mailLength[i] = mail.get(i).getContent().length();
                        }
                    }
                } else if (str.contains("read")) {
                    if (str.equals("read")) {
                        sendDataToClient(err);
                    } else {
                        int mail_id = Integer.parseInt(str.substring(5));
                        boolean res = mailDao.readMail(mail_id);
                        mail = mailDao.getMails(user);
                        System.out.println("Mail has been read");
                        sendDataToClient(res);
                    }
                } else if (str.contains(";")){ // 账号密码
                    String [] mess = str.split(";");
                    String username = mess[0];
                    String password = mess[1];
                    sendDataToClient(find_user(username, password));
                }
                else {
                    System.out.println("unknow command");
                    sendDataToClient(err);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private User find_user(String name, String pass){
        return new UserImpl().login(name, pass);
    }

    private boolean welcomeAndLogin(User user) {
        UserDao userDao = new UserImpl();
        user = userDao.login(user.getUsr_id(), user.getPassword());
        if (user == null) {
//            sendMsgToMe("-ERR user");
            flag=false;
            return false;
        } else {
            System.out.println("welcome: "+user.getUsrname());
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
