import java.sql.*;
import java.util.Hashtable;

public class DbConnection {
	private static Connection conn;
	private static int successFlag = 0;
	
	public static void db_initialize(Hashtable<String, String> dbIni) {
		try {
			String hostName = dbIni.get("hostname");
			String port = dbIni.get("port");
			String databaseName = dbIni.get("databasename");
			String username = dbIni.get("username");
			String password = dbIni.get("password");
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + hostName + ":" + port + "/" + databaseName
					+ "?useLegacyDatetimeCode=false&serverTimezone=Asia/Singapore",
					username, password);
			
			successFlag = 1;
			System.out.println("SQL Connection established.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection get_conn() {
		return conn;
	}
	
	public static int get_successFlag() {
		return successFlag;
	}
}
