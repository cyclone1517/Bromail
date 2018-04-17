package JavaDao;

import JavaBean.Friend;

public interface FriendDao {
    static enum blackFlag {NO, YES}; //黑名单标志位默认 0不黑名 -1黑名

    public boolean addFriend(String userId, String friendId, String remarkName);

    public boolean deleteFriend(String userId, String friendId);

    public boolean blacklistFriend(String userId, String friendId);
    //加入权限选择，调用时参数用语句FriendDao.blackFlag.YES/NO;

    public boolean updateRemarkName(String userId, String friendId, String remarkName);
    //修改备注名

    public Friend searchFriend(String userId, String remarkName);
    //搜索关键字可以是id可以是备注名
}
