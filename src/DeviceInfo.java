import java.util.Date;

public class DeviceInfo {
	private int id;
	private String name;
	private String dateInstalled;
	private double cost;
	private int reportInterval;
	private String remarks;
	
	/**
	 * This sets device info values.
	 * @param id
	 * @param name
	 * @param dateInstalled
	 * @param cost
	 * @param reportInterval
	 * @param remarks
	 */
	public DeviceInfo(int id, String name, String dateInstalled, 
			double cost, int reportInterval, String remarks) {
		setId(id);
		setName(name);
		setDateInstalled(dateInstalled);
		setCost(cost);
		setReportInterval(reportInterval);
		setRemarks(remarks);
	}
	
	/**
	 * Gets id.
	 * @return the id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets id.
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets date installed.
	 * @return the date installed.
	 */
	public String getDateInstalled() {
		return dateInstalled;
	}
	
	/**
	 * Sets date installed.
	 * @param dateInstalled
	 */
	public void setDateInstalled(String dateInstalled) {
		this.dateInstalled = dateInstalled;
	}
	
	/**
	 * Gets cost.
	 * @return the cost.
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Sets cost.
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * Gets report interval.
	 * @return the report interval.
	 */
	public int getReportInterval() {
		return reportInterval;
	}
	
	/**
	 * Sets report interval.
	 * @param reportInterval
	 */
	public void setReportInterval(int reportInterval) {
		this.reportInterval = reportInterval;
	}
	
	/**
	 * Gets remarks.
	 * @return the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	
	/**
	 * Sets remarks.
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
