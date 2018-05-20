package JavaDao;

import JavaBean.Mail;
import JavaBean.User;

import java.util.List;

public interface MailDao {
    void storeMail(Mail mail);
    List<Mail> getMails(User user);//or void getMail();all users
    List<Mail> getSentOrDraftMails(User user, int sendStat);//get sent Mails by one user

    Mail getMail(int mailId);
    void deleMail(int mail_id);
}
