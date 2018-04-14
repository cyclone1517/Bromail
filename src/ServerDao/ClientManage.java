package ServerDao;

public interface ClientManage {
	//int: account  int: authority

	public boolean createAccount();		//随机分配账号，可从1000递增，默认账号密码相同，权限为普通
	public boolean createAccount(int authority); 	//0-普通； 1-管理员
	
	public boolean deleteAccount(int account);	//删除账号

	public boolean stopAccount(int account);
	
	public boolean changePassword(int account, String password);
	
	public boolean setAuthority(int authority);
	
}
