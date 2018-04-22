package JavaDao;

import JavaBean.Mail;
import JavaBean.User;

import java.util.List;

public interface MailDao {
    void storeMail(Mail mail);
    List<Mail> getMail(User user);//or void getMail();all users
    void deleMail(String mail_id);
}
