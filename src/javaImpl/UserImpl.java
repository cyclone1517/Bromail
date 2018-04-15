package javaImpl;

import javaBean.User;
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
    public boolean addUser(String userId, String password) {
        return false;
    }

    @Override
    public boolean addUser(String userId, String password, int authority) {
        return false;
    }

    @Override
    public boolean updateUser(String userId) {
        return false;
    }

    @Override
    public boolean searchUser(String userId) {
        return false;
    }
}
