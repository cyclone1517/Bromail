package JavaDao;

public interface FriendDao {
    //黑名单标志位默认 0不黑名 -1黑名

    public boolean addFriend(String userId, String friendId);

    public boolean deleteFriend(String userId, String friendId);

    public boolean updateRemarkName(String remarkName);
    //修改备注名

    public boolean searchFiend(String FriendIdOrName);
    //搜索关键字可以是id可以是备注名
}
