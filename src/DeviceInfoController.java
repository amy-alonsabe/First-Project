import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DeviceInfoController {
	
	/**
	 * Returns the contents of the device_info table.
	 * @return an ArrayList that contains the rows of device_info table.
	 */
	public static ArrayList<DeviceInfo> get_deviceInfo() {
		// Initialize variables.
		ArrayList<DeviceInfo> deviceInfoList = new ArrayList<>();
		Statement stm = null;
		ResultSet rs = null;
		
		// Connect to database.
		Connection conn = DbConnection.db_connect();
		
		// Connection is successful.
		if (conn != null) {
			System.out.println("Successfully established database connection.");
			
			// Perform the database query to get table rows.
			try {
				stm = conn.createStatement();
				String query = "SELECT * FROM device_info";
				rs = stm.executeQuery(query);
				
				while (rs.next()) {
					DeviceInfo deviceInfo = new DeviceInfo(rs.getInt("id"), 
							rs.getString("name"), rs.getString("date_installed"),
							rs.getDouble("cost"), rs.getInt("report_interval"),
							rs.getString("remarks"));
					deviceInfoList.add(deviceInfo);
				}
			}
			// Error performing the query.
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Failed to load table.");
				System.out.println("Failed to load device_info rows.");
				return null;
			}
			// Close connections.
			finally {
//				JOptionPane.showMessageDialog(null, "Table loaded.");
				System.out.println("Successfully loaded device_info rows");
				System.out.println("Closing database connections...");
				DbConnection.close_connections(conn, stm, null, rs);
			}
			
			return deviceInfoList;
		}
		// Connection is null.
		else {
			//try to reconnect 3 times before raising the error (apply to all db connection)
			JOptionPane.showMessageDialog(null, "Failed to load table.");
			System.out.println("Failed to establish database connection.");
			return null;
		}
	}
	
	/**
	 * Adds row to the device_info table.
	 * @param data are the values to be added in the database.
	 */
	public static void set_deviceInfo(DeviceInfo data) {
		// Initialize variable
		PreparedStatement prepStm = null;
		
		// Connect to database.
		Connection conn = DbConnection.db_connect();
		
		// Connection is successful.
		if (conn != null) {
			System.out.println("Successfully established database connection.");
			
			// Perform the database query to add table rows.
			try {
				String query = "INSERT INTO device_info (name, date_installed, cost," +
						" report_interval, remarks) VALUES (?, ?, ?, ?, ?)";
				prepStm = conn.prepareStatement(query);
				
				prepStm.setString(1, data.getName());
				prepStm.setTimestamp(2, java.sql.Timestamp.valueOf(data.getDateInstalled()));
				prepStm.setDouble(3, data.getCost());
				prepStm.setInt(4, data.getReportInterval());
				prepStm.setString(5, data.getRemarks());
				
				prepStm.execute();
			}
			// Error performing the query.
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Failed to insert row.");
				System.out.println("Failed to insert data into device_info.");
			}
			// Close connections.
			finally {
				JOptionPane.showMessageDialog(null, "Row inserted.");
				System.out.println("Successfully inserted data into device_info.");
				System.out.println("Closing database connections...");
				DbConnection.close_connections(conn, null, prepStm, null);
			}
		}
		// Connection is null.
		else {
			JOptionPane.showMessageDialog(null, "Failed to insert row.");
			System.out.println("Failed to establish database connection.");
		}
	}
	
	/**
	 * Updates a row in device_info table.
	 * @param data contains values for the update.
	 * @param id is the id of the to be updated row.
	 */
	public static void update_deviceInfo(DeviceInfo data, int id) {
		// Initialize variable
		PreparedStatement prepStm = null;
		
		// Connect to database.
		Connection conn = DbConnection.db_connect();
		
		// Connection successful.
		if (conn != null) {
			System.out.println("Successfully established database connection.");
			
			// Perform the database query to update a table row.
			try {
				String query = "UPDATE device_info SET name=?, date_installed=?, " +
						"cost=?, report_interval=?, remarks=? WHERE id=?";
				prepStm = conn.prepareStatement(query);
				
				prepStm.setString(1, data.getName());
				prepStm.setTimestamp(2, java.sql.Timestamp.valueOf(data.getDateInstalled()));
				prepStm.setDouble(3, data.getCost());
				prepStm.setInt(4, data.getReportInterval());
				prepStm.setString(5, data.getRemarks());
				prepStm.setInt(6, data.getId());
				
				prepStm.execute();
			}
			// Error performing the query.
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Failed update row.");
				System.out.println("Failed to update data into device_info.");
			}
			// Close connections.
			finally {
				JOptionPane.showMessageDialog(null, "Row updated.");
				System.out.println("Successfully updated data into device_info.");
				System.out.println("Closing database connections...");
				DbConnection.close_connections(conn, null, prepStm, null);
			}
		}
		// Connection is null.
		else {
			JOptionPane.showMessageDialog(null, "Failed update row.");
			System.out.println("Failed to establish database connection.");
		}
	}
	
	/**
	 * Deletes a row in device_info table.
	 * @param id is the id of the to be deleted row.
	 */
	public static void delete_deviceInfo(int id) {
		// Initialize variable
		PreparedStatement prepStm = null;
		
		// Connect to database.
		Connection conn = DbConnection.db_connect();
		
		// Connection is successful.
		if (conn != null) {
			System.out.println("Successfully established database connection.");
			
			// Perform the database query to update a table row.
			try {
				String query = "DELETE FROM device_info WHERE id=?";
				prepStm = conn.prepareStatement(query);
				
				prepStm.setInt(1, id);
				
				prepStm.execute();
			}
			// Error performing the query.
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Failed to delete row.");
				System.out.println("Failed to delete device_info row.");
			}
			// Close connections
			finally {
				JOptionPane.showMessageDialog(null, "Row deleted.");
				System.out.println("Successfully deleted device_info row.");
				System.out.println("Closing database connections...");
				DbConnection.close_connections(conn, null, prepStm, null);
			}
		}
		// Connection is null.
		else {
			JOptionPane.showMessageDialog(null, "Failed to delete row.");
			System.out.println("Failed to establish database connection.");
		}
	}
}
