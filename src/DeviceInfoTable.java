import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DeviceInfoTable extends JFrame implements ActionListener {
	private static final String INI_FILE = "inifile.ini";
	
	private JPanel buttons;
	private JButton add, edit, delete;
	private JScrollPane scrollPane;
	private JTable deviceInfo;
	private static DefaultTableModel deviceInfoModel;
	private String column[] = new String[] {"ID", "NAME", "DATE INSTALLED", "COST", 
			"REPORT INTERVAL", "REMARKS"};
	
	public DeviceInfoTable() {
		super("GPS Device Info");
		setSize(1080, 520);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		deviceInfoModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
		    }
		};
		deviceInfoModel.setColumnIdentifiers(column);
		deviceInfo = new JTable();
		deviceInfo.setModel(deviceInfoModel);
		deviceInfo.setSelectionModel(new ForcedListSelectionModel());
		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(deviceInfo);
		add(BorderLayout.NORTH, scrollPane);
		
		buttons = new JPanel();
		add = new JButton("Add");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		add.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		buttons.add(add);
		buttons.add(edit);
		buttons.add(delete);
		add(BorderLayout.SOUTH, buttons);
		
		setVisible(true);
		update_table();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Add":
				this.dispose();
				new DeviceInfoAdd();
				break;
			case "Edit":
				System.out.println(deviceInfoModel.getDataVector().elementAt(deviceInfo.getSelectedRow()));
				break;
			case "Delete":
				break;
		}
	}
	
	public static void update_table() {
		ArrayList<DeviceInfo> deviceInfo = DeviceInfoController.get_deviceInfo();
		
		for(DeviceInfo data : deviceInfo) {
			Object rowData[] = { data.getId(), data.getName(),
					data.getDateInstalled(), data.getCost(),
					data.getReportInterval(), data.getRemarks() };
			deviceInfoModel.addRow(rowData);
		}
		
		deviceInfoModel.fireTableDataChanged();
	}
	
	public class ForcedListSelectionModel extends DefaultListSelectionModel {

	    public ForcedListSelectionModel () {
	        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	    @Override
	    public void clearSelection() {
	    }

	    @Override
	    public void removeSelectionInterval(int index0, int index1) {
	    }

	}
	
	public static void main(String[] args) {
		DbConnection.db_initialize(ReadIni.read_ini(INI_FILE));
		
		new DeviceInfoTable();
	}
}

