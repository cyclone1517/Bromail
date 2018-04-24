package JavaDao;

import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

public interface LogDao {
    enum LogType {POP , SMTP}
    
    public boolean addLog(Timestamp create_Date, LogType logType, Socket client);    //id自增，添加条目时缺省

    public boolean modifyLog(Date modify_Date);

    public boolean deleteLog();

    public boolean clearLog();
}
