package JavaBean.Entity;

import java.io.Serializable;

public class Friend implements Serializable {
    private String usr_id;
    private String friend_id;
    private int blackFlag;
    private String remarkName;
    public String getRemarkName(){return remarkName;}
    public void setRemarkName(String remarkName){this.remarkName=remarkName;}
    public String getUsr_id() {
        return usr_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public int getBlackFlag() {
        return blackFlag;
    }

    public void setUsr_id(String usr_id) {
        this.usr_id = usr_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public void setBlackFlag(int blackFlag) {
        this.blackFlag = blackFlag;
    }
}
