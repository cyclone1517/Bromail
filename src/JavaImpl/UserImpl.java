package JavaImpl;

import JavaBean.User;
import JavaDao.UserDao;
import Utils.ConnDBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserImpl implements UserDao {

    @Override
    public User login(String userId, String password) {
        String sql="select * from USER where usr_id=? and password=?";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();

        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, userId);
            ptmt.setString(2, password);
            ResultSet rs = ptmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsr_id(rs.getString("usr_id"));
                user.setUsrname(rs.getString("usrname"));
                user.setPassword(rs.getString("password"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            util.closeConnection(conn);
        }
        return null;
    }

    @Override
    public boolean addUser(String userId, String userName, String password, int authority) {    //默认创建普通用户
        String sql="insert into USER(usr_id, usrname, password , authority) VALUES (?, ?, ?, ?)";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();

        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, userId);
            ptmt.setString(2, userName);
            ptmt.setString(3, password);
            ptmt.setInt(4, authority);
            ptmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            util.closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        String sql ="update USER set usrname=?, password=?, authority=? where usr_id=?";
        ConnDBUtil util=new ConnDBUtil();
        Connection conn=util.openConnection();
        try {
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setString(1, user.getUsrname());
            ptmt.setString(2, user.getPassword());
            ptmt.setInt(3, user.getAuthority());
            ptmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            util.closeConnection(conn);
        }
        return false;
    }

    @Override
    public boolean searchUser(String userId) {
        return false;
    }
}
