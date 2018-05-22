package JavaBean.Dao;

import JavaBean.Entity.Mail;
import JavaBean.Entity.User;

import java.util.List;

public interface MailDao {
    void storeMail(Mail mail);
    List<Mail> getMails(User user);//or void getMail();all users
    List<Mail> getSentOrDraftMails(User user, int sendStat);//get sent Mails by one user

    Mail getMail(int mailId);
    boolean deleMail(int mail_id);
    boolean readMail(int mail_id);
}
