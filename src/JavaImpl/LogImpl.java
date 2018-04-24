package JavaImpl;

import JavaDao.LogDao;
import Utils.ConnDBUtil;

import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class LogImpl implements LogDao{

    @Override
    public boolean addLog(Timestamp create_Date, LogType logType, Socket client) {
        System.out.println("[LogImpl]Running...");
        String sql="insert into Log(popornot, create_Date, modify_date, content) values(?, ?, ?, ?)";
        ConnDBUtil util = new ConnDBUtil();
        Connection conn = util.openConnection();
        String content = client.getInetAddress().toString();

        try{
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, logType.ordinal());
            psmt.setTimestamp(2, create_Date);
            psmt.setTimestamp(3, create_Date);  //新建记录修改时间就是添加时间
            psmt.setString(4, content);
            System.out.println(psmt);
            psmt.execute();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            util.closeConnection(conn);
        }
        return false;
    }

    private String geneContent(Timestamp create_Date, LogType logType){
        return create_Date.toString() + "," + logType.toString() + ",fromIp:" + "not finished yet";
    }

    @Override
    public boolean modifyLog(Date modify_Date) {
        return false;
    }

    @Override
    public boolean deleteLog() {
        return false;
    }

    @Override
    public boolean clearLog() {
        return false;
    }
}
