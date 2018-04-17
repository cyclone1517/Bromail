package JavaImpl;

import JavaBean.Friend;
import JavaDao.FriendDao;
import Utils.ConnDBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public Friend searchFriend(String userId, String remarkName){
        String sql = "select * from FRIEND where userId=? and remarkName=?";
        ConnDBUtil util = new ConnDBUtil();
        Connection conn = util.openConnection();
        ResultSet rs = null;
        Friend friend = new Friend();

        try{
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, remarkName);
            rs = psmt.executeQuery();
            while(rs.next()){
                friend.setFriend_id(rs.getString("friend_id"));
                friend.setRemarkName(rs.getString("remarkName"));
                friend.setBlackFlag(rs.getInt("blackFlag"));
            }

        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            util.closeConnection(conn);
        }
        return friend;
    }
}
