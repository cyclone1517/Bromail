package JavaImpl;

import JavaBean.Entity.Mail;
import JavaBean.Entity.User;
import JavaBean.Dao.MailDao;
import Utils.ConnDBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MailImpl implements MailDao {
    @Override
    public void storeMail(Mail mail) {
        String sql = "insert into MAIL(sender, receiver, time, subject, content) values(?,?,?,?,?)";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();

        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            for (String receiver:mail.getToList()) {
                ptmt.setString(1, mail.getFrom());
                ptmt.setString(2, receiver);
                ptmt.setTimestamp(3,mail.getTime());
                ptmt.setString(4,mail.getSubject());
                ptmt.setString(5,mail.getContent());
                ptmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public List<Mail> getMails(User user){
        String sql ="select * from MAIL where receiver = ? ORDER BY mail_id DESC ";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();
        List<Mail> mail=new ArrayList<Mail>();
        try{
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,user.getUsr_id());
            ResultSet rs=ptmt.executeQuery();
            while(rs.next()){
                Mail m=new Mail();
                m.setContent(rs.getString("content"));
                m.setFrom(rs.getString("sender"));
                m.setTo(rs.getString("receiver"));
                m.setTime(rs.getTimestamp("time"));
                m.setSubject(rs.getString("subject"));
                m.setMail_id(rs.getInt("mail_id"));
                m.setReadStat(rs.getInt("readStat"));
                m.setSendStat(rs.getInt("sendStat"));
                mail.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return mail;
    }

    @Override
    public List<Mail> getSentOrDraftMails(User user, int sendStat) {
        String sql ="select * from MAIL where sender = ? and sendStat = ? ORDER BY mail_id DESC ";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();
        List<Mail> mail=new ArrayList<Mail>();
        try{
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,user.getUsr_id());
            ptmt.setInt(2,sendStat);
            ResultSet rs=ptmt.executeQuery();
            while(rs.next()){
                Mail m=new Mail();
                m.setContent(rs.getString("content"));
                m.setFrom(rs.getString("sender"));
                m.setTo(rs.getString("receiver"));
                m.setTime(rs.getTimestamp("time"));
                m.setSubject(rs.getString("subject"));
                m.setMail_id(rs.getInt("mail_id"));
                m.setReadStat(rs.getInt("readStat"));
                m.setSendStat(rs.getInt("sendStat"));
                mail.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return mail;
    }

    @Override
    public Mail getMail(int mailId) {
        String sql = "select * from MAIL where mail_id = ? ";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();
        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1,mailId);
            ResultSet rs=ptmt.executeQuery();
            Mail m = new Mail();
            m.setContent(rs.getString("content"));
            m.setFrom(rs.getString("sender"));
            m.setTo(rs.getString("receiver"));
            m.setTime(rs.getTimestamp("time"));
            m.setSubject(rs.getString("subject"));
            m.setMail_id(rs.getInt("mail_id"));
            m.setReadStat(rs.getInt("readStat"));
            m.setSendStat(rs.getInt("sendStat"));
            return m;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleMail(int mail_id){
        String sql ="delete from MAIL where mail_id = ?";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1,mail_id);
            ptmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean readMail(int mail_id) {
        String sql ="update MAIL set readStat=1 where mail_id = ?";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1,mail_id);
            ptmt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
