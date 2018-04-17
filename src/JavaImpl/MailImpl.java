package JavaImpl;

import JavaBean.Mail;
import JavaDao.MailDao;
import Utils.ConnDBUtil;

import java.sql.*;

public class MailImpl implements MailDao {
    @Override
    public void storeMail(Mail mail) {
        String sql = "insert into mail(sender, receiver, time, subject, content) values(?,?,?,?,?)";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();

        PreparedStatement ptmt = null;
        try {
            ptmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ptmt.setString(1, mail.getFrom());
            ptmt.setString(2, mail.getTo());
            ptmt.setTimestamp(3,mail.getTime());
            ptmt.setString(4,mail.getSubject());
            ptmt.setString(5,mail.getContent());
            ptmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
