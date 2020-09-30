import java.sql.*;
import java.util.ArrayList;

public class DeviceInfoController {
	private static int successFlag = 0;
	
	public static ArrayList<DeviceInfo> get_deviceInfo() {
		ArrayList<DeviceInfo> deviceInfoList = new ArrayList<>();
		
		try {
			// add checking if successfully connected to database. if not, retry 3 times. If still not connected raise an error
			Connection conn = DbConnection.get_conn();
			Statement stm;
			stm = conn.createStatement();
			
			String query = "SELECT * FROM device_info";
			ResultSet rs;
			rs = stm.executeQuery(query);
			
			while(rs.next()) {
				DeviceInfo deviceInfo = new DeviceInfo(rs.getInt("id"), 
						rs.getString("name"), rs.getString("date_installed"),
						rs.getDouble("cost"), rs.getInt("report_interval"),
						rs.getString("remarks"));
				deviceInfoList.add(deviceInfo);
			}
			
			successFlag = 1;
		}
		catch(Exception e) {
			successFlag = 0;
			e.printStackTrace();
		}
		// add finally clause to close resultset, statement and connection. If this is omitted, it will result to OOM error
		
		return deviceInfoList;
	}
	
	public static void set_deviceInfo(ArrayList<DeviceInfo> data) {
		try {
			// add checking if successfully connected to database. if not, retry 3 times. If still not connected raise an error
			Connection conn = DbConnection.get_conn();
			String query = "INSERT INTO device_info (name, date_installed, cost," +
					" report_interval, remarks) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement prepStm = conn.prepareStatement(query);
			
			for(DeviceInfo d : data) {
				prepStm.setString(1, d.getName());
				prepStm.setTimestamp(2, java.sql.Timestamp.valueOf(d.getDateInstalled()));
				prepStm.setDouble(3, d.getCost());
				prepStm.setInt(4, d.getReportInterval());
				prepStm.setString(5, d.getRemarks());
				
				prepStm.execute();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		// add finally clause to close resultset, statement and connection. If this is omitted, it will result to OOM error
	}
	
	public static int get_successFlag() {
		return successFlag;
	}
}
