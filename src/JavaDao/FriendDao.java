package JavaDao;

import JavaBean.FriendInfo;

import java.util.List;

public interface FriendDao {
    static enum blackFlag {NO, YES}; //黑名单标志位默认 0不黑名 -1黑名

    public boolean addFriend(String userId, String friendId, String remarkName);

    public boolean deleteFriend(String userId, String friendId);

    public boolean blacklistFriend(String userId, String friendId);
    //加入权限选择，调用时参数用语句FriendDao.blackFlag.YES/NO;

    public boolean updateRemarkName(String userId, String friendId, String remarkName);
    //修改备注名

    /**
     * @author YukonChen
     * 两个参数：用户id，朋友关键字(可以是userId|userName|remarkName)
     * 如QQ搜索2015，会返回一个list，优先显示备注含2015的，再显示QQ名含2015的，最后显示QQ号含2015的
     * 如查到：2015级软件三班班群-> 2015年的美少女 -> XXX（QQ：XX2015XXXX）
     */
    public List<FriendInfo> searchFriend(String userId, String keyword);
    //搜索关键字可以是id可以是备注名
}
