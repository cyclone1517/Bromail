package JavaBean.Dao;

import JavaBean.Entity.User;

public interface UserDao {
	enum authorities {MANAGER, USER, STOP};	//权限常量0管理员-1用户-2禁用

	public User login(String userId, String password);

	//public boolean addUser(String userId, String password);	//默认方法
	public boolean addUser(String userId, String userName, String password, int authority);
	//加入权限选择，调用时参数用语句UserDao.authorities.USER/MANAGER/STOP

	public boolean updateUser(User user);	//账号一经分配，不可修改
	//设置昵称，修改密码，修改权限，禁用用户等

	public boolean searchUser(String userId);
}
