package ServerInterface;

public interface MailService {

	public boolean sendMailToAll();	//群发邮件, 仅限管理员
	
	public boolean sendEmail();		//基于SMTP实现
	
	public boolean receiveEmail();	//基于POP3实现
	
	public boolean addFriend(int account);
	
	public boolean deleteFriend(int account);
	
	public boolean searchFriend(int account);
	
	public boolean setFriendLabel(int account, String label);
	
	public boolean addAccountBlackList(int account);	//根据账号过滤邮件
	
	public boolean addIPBlackList(String IP);	//idea不能识别String?
}