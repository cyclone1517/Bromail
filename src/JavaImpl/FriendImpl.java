package JavaImpl;

import JavaBean.Entity.FriendInfo;
import JavaBean.Dao.FriendDao;
import Utils.ConnDBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FriendImpl implements FriendDao{
    @Override
    public boolean addFriend(String userId, String friendId, String remarkName){
        String sql="insert into FRIEND(usr_id, friend_id, remarkName, blackFlag) values(?, ?, ?, 0)";
        ConnDBUtil util = new ConnDBUtil();
        Connection conn = util.openConnection();

        try{
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, friendId);
            psmt.setString(3, remarkName);
            psmt.execute();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            util.closeConnection(conn);
        }
        return false;
    }
    @Override
    public  boolean updateRemarkName(String userId, String friendId, String remarkName){
        String sql="update FRIEND set remarkName=? where usr_Id=? and friend_Id=?";
        ConnDBUtil util = new ConnDBUtil();
        Connection conn = util.openConnection();

        try{
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, remarkName);
            psmt.setString(2, userId);
            psmt.setString(3, friendId);
            psmt.executeUpdate();
            return  true;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        return false;

    }

    @Override
    public  boolean deleteFriend(String userId, String friendId){
        String sql = "delete from FRIEND where usr_Id=? and friend_id=?";
        ConnDBUtil util = new ConnDBUtil();
        Connection conn = util.openConnection();

        try{
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, friendId);
            psmt.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean blacklistFriend(String userId, String friendId){
        String sql = "update FRIEND set blackFlag=1 where usr_id=? and friend_id=?";
        ConnDBUtil util = new ConnDBUtil();
        Connection conn = util.openConnection();

        try{
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, friendId);
            psmt.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        return false;
    }

    @Override
    /**
     * @author: YukonChen
     * 将关键字按照备注名、用户名、用户id搜索三次
     * 如果有错rs可能是潜在bug
     * 虽然搜索的是朋友，但是需要返回朋友信息类，以便进行其它操作
     */
    public List<FriendInfo> searchFriend(String userId, String keyword){
        String[] sql = new String[3];
        keyword = "%" + keyword + "%";
        sql[0] = "select * from FRIEND where usr_id=? and remarkName like ?";
        sql[1] = "select * from FRIEND f, User u where f.usr_id=? " +
                "and f.friend_id = u.usr_id " +
                "and u.usrname like ?";
        //sql[1]搜索 userId所有朋友中用户名为keyword的
        sql[2] = "select * from FRIEND where usr_id=? and friend_id like ?";
        List<FriendInfo> friendList = new ArrayList<>();
        Set<String> friendSet = new HashSet<>();
        //HashSet重复检测，如果相同关键字，同一个朋友多次被检测，仅显示一次

        ConnDBUtil util = new ConnDBUtil();
        Connection conn = util.openConnection();
        ResultSet rs = null;
        String keywordRst = null;

        try{
            for(int i=0; i<3; i++){
                PreparedStatement ptmt = conn.prepareStatement(sql[i]);
                ptmt.setString(1, userId);
                ptmt.setString(2, keyword);
                rs = ptmt.executeQuery();
                System.out.println(ptmt);
                while(rs.next()){
                    String friend_id = rs.getString("friend_id");
                    int blackFlag = rs.getInt("blackFlag");
                    //关键字结果（备注名|用户名|朋友id）多种情况，用switch区分
                    switch (i){
                        case 0:
                            keywordRst = rs.getString("remarkName");
                            break;
                        case 1:
                            keywordRst = rs.getString("usrname");
                            break;
                        case 2:
                            keywordRst = friend_id;
                            break;
                    }
                    System.out.println("i=" + i + ", keywordRst" + keywordRst);
                    if(friendSet.contains(friend_id)){
                        System.out.println("discarded: " + keywordRst);
                        continue;
                    }
                    else{
                        System.out.println("got: " + keywordRst);
                        friendSet.add(friend_id);
                        friendList.add(new FriendInfo(friend_id, keywordRst));
                    }
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        if(friendList.size()==0) return null;
        return friendList;
    }
}
