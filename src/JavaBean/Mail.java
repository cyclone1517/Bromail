package JavaBean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Mail {
    private String mail_id;
    private String from;
    private String to;
    private String[] toList;
    private String subject;
    private String content;
    private Timestamp time;

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String[] getToList() {
        return toList;
    }

    public void setToList(String[] toList) {
        this.toList = toList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public  void setSubject(String subject) {
        this.subject = subject;
    }

    public Mail() {
    }

    public Mail(String from, String[] toList, String subject, String content) {
        this.from = from;
        this.toList = toList;
        this.subject = subject;
        this.content = content;
    }
}
