package JavaImpl;

import JavaBean.Mail;
import JavaBean.User;
import JavaDao.MailDao;
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
    public List<Mail> getMail(User user){
        String sql ="select * from MAIL where receiver = ?";
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
                mail.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return mail;
    }
    public   void deleMail(int mail_id){
        String sql ="delete from MAIL where mail_id = ?";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1,mail_id);
            ptmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

}
