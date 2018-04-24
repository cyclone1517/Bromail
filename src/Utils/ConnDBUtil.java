package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnDBUtil {
	public Connection openConnection(){
		Properties prop =new Properties();
		String driver=null;
		String url=null;
		String username=null;
		String password=null;
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("Config.properties"));
			driver=prop.getProperty("driver");
			url=prop.getProperty("url");
			username=prop.getProperty("username");
			password=prop.getProperty("password");
			Class.forName(driver);
			return DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//DriverManager.getConnection("jdbc:mysql://localhost:3306/jsp_db?useSSL=false","root","123456");
	public void closeConnection(Connection conn){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
