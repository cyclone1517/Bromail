package JavaDao;

import javaBean.User;

public interface UserDao {
	static enum authorities {USER, MANAGER, STOP};	//权限常量0用户-1管理员-2禁用
	
	public User login(String userId, String password);

	public boolean addUser(String userId, String password);	//默认方法
	public boolean addUser(String userId, String password, int authority);
	//加入权限选择，调用时参数用语句UserDao.authorities.USER/MANAGER/STOP

	public boolean updateUser(String userId);
	//设置昵称，修改密码，修改权限，禁用用户等

	public boolean searchUser(String userId);
}
