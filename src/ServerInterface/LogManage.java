package ServerInterface;

import JavaDao.LogDao;

import java.net.Socket;

public interface LogManage {
	//参数类型根据文件储存状况或路径待定
	public boolean addLog(LogDao.LogType logType, Socket client);

//	public boolean getSMTPLog();
//	public boolean getPOP3Log();
//
//	public boolean cleanSMTPLog();
//	public boolean cleanPOP3Log();
//
//	public boolean cleanAllSMTPLog();
//	public boolean cleanAllPOP3Log();
//
//	public boolean setSMTPLogLocation();
//	public boolean setPOP3LogLocation();
//
//	public boolean setSMTPLogSpace(); 	//设置日志大小
//	public boolean setPOP3LogSpace();
}
