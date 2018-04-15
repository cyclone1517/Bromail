package ServerInterface;

public interface ClientManage {

	public boolean createUser(String userId, String password);
	//随机分配账号，可从1000递增，默认账号密码相同，权限为普通

	public boolean createUser(String userId, String password, int authority);
	
	public boolean deleteUser(int userId);	//删除账号

	public boolean stopUser(int userId);
	
	public boolean changePassword(int userId, String password);
	
	public boolean setAuthority(int authority);
	
}
