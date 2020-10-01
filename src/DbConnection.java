import java.io.FileInputStream;
import java.sql.*;
import java.util.Hashtable;
import java.util.Properties;

public class DbConnection {
	// Location/name of the .ini file.
	private static final String INI_FILE = "inifile.ini";
	// The max number of database connection retries.
	private static final int MAX_RETRY = 3;
	
	/**
	 * Returns the database connection.
	 * @return a connection with a database.
	 */
	public static Connection db_connect() {
		Connection conn = null;
		
		// Get the properties of .ini file.
		Properties properties = load_ini();
		
		// you may use global variables for these database related variables. then set/read the inifile on load of the app
		// so that you dont need to read the inifile every time you need to connect to the database
		
		// Extract the properties of .ini file.
		String hostName = properties.getProperty("hostname");
		String port = properties.getProperty("port");
		String databaseName = properties.getProperty("databasename");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		
		// Try to establish database connection.
		try {
			// Initialize current number of retries.
			int retryCount = 0;
			
			// Repeat if no connection until max attempt is reached.
			while (conn == null && retryCount < MAX_RETRY) {
				// Get connection.
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://" + hostName + ":" + port + "/" + databaseName
						+ "?useLegacyDatetimeCode=false&serverTimezone=Asia/Singapore",
						username, password);
				
				// If no connection is established, increment number of retries and wait for
				// a fixed amount of time
				if (conn == null) {
					System.out.println("Error connecting to database. Retrying...");
					retryCount++;
					Thread.sleep(1000);
				}
			}
		}
		// Error getting connection.
		catch (Exception e) {
			// Empty
		}
		
		return conn;
	}
	
	/**
	 * Closes the database connections.
	 * @param conn is the Connection to be closed.
	 * @param stm is the Statement to be closed.
	 * @param ps is the PreparedStatement to be closed.
	 * @param rs is the ResultSet to be closed.
	 */
	public static void close_connections(Connection conn, Statement stm, PreparedStatement ps, ResultSet rs) {
		if (conn != null) {
	        try {
	            conn.close();
	        } 
	        catch (SQLException e) {
	        	// Empty
	        }
		}
		if (stm != null) {
	        try {
	            stm.close();
	        } 
	        catch (SQLException e) {
	        	// Empty
	        }
		}
		if (ps != null) {
	        try {
	            ps.close();
	        } 
	        catch (SQLException e) {
	        	// Empty
	        }
		}
		if (rs != null) {
	        try {
	            rs.close();
	        } 
	        catch (SQLException e) {
	        	// Empty
	        }
		}
	}
	
	/**
	 * Returns the properties of .ini file.
	 * @return the properties of loaded .ini file.
	 */
	private static Properties load_ini() {
		Properties p = new Properties();
		
		// Try to load file.
		try {
			p.load(new FileInputStream(INI_FILE));
		}
		// Error loading the file.
		catch (Exception e) {
			System.out.println("Error loading inifile.ini");
		}
		
		return p;
	}
}
