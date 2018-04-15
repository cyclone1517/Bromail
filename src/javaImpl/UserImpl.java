package javaImpl;

import JavaDao.UserDao;
import javaBean.User;

public class UserImpl implements UserDao {

    @Override
    public User login(String userId, String password) {
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
