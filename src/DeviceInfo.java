import java.util.Date;

public class DeviceInfo {
	private int id;
	private String name;
	private String dateInstalled;
	private double cost;
	private int reportInterval;
	private String remarks;
	
	public DeviceInfo(int id, String name, String dateInstalled, 
			double cost, int reportInterval, String remarks) {
		setId(id);
		setName(name);
		setDateInstalled(dateInstalled);
		setCost(cost);
		setReportInterval(reportInterval);
		setRemarks(remarks);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDateInstalled() {
		return dateInstalled;
	}
	
	public void setDateInstalled(String dateInstalled) {
		this.dateInstalled = dateInstalled;
	}
	
	public double getCost() {
		return cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public int getReportInterval() {
		return reportInterval;
	}
	
	public void setReportInterval(int reportInterval) {
		this.reportInterval = reportInterval;
	}
	
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
