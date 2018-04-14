package ServerDao;

public interface ServiceManage {

	public boolean stopSMTP();
	public boolean startSMTP();
	
	public boolean stopPOP3();
	public boolean startPOP3();
	
	public boolean startServer(int port);	//停止服务后仅管理员可登陆?
	public boolean stopServer();
}
