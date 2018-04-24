package JavaBean;

import java.util.Date;

public class Log {
    private String log_id;
    private String content;
    private Date create_date;
    private Date modify_date;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getContent_id() {
        return content;
    }

    public void setContent_id(String content_id) {
        this.content = content_id;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }
}
