package ServerImpl;

import JavaDao.LogDao;
import JavaImpl.LogImpl;
import ServerInterface.LogManage;

import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

public class LogManageImpl implements LogManage {

    @Override
    public boolean addLog(LogDao.LogType logType, Socket client) {
        LogDao ld = new LogImpl();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        ld.addLog(timestamp, logType, client);

        return false;
    }
    
}
