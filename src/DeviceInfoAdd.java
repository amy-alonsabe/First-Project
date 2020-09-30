import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.RoundingMode;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DeviceInfoAdd extends JFrame implements ActionListener, PropertyChangeListener {
	private JPanel formPanel, buttons;
	private JLabel nameLbl, dateInstalledLbl, costLbl, reportIntervalLbl, 
				remarksLbl;
	private Font font;
	private JFormattedTextField cost, reportInterval, name, dateInstalled;
	private NumberFormat costFormat, reportIntervalFormat;
	private DateFormat dateInstalledFormat;
	private JTextArea remarks;
	private JScrollPane remarksScroll;
	private JButton save, back;
	private double lastValidCost = 0;
	private int lastValidRi = 0;
	
	public DeviceInfoAdd() {
		super("Add GPS Device Info");
		setResizable(false);
		setLocationRelativeTo(null);
		
		nameLbl = new JLabel("Name", JLabel.TRAILING);
		dateInstalledLbl = new JLabel("Date Installed", JLabel.TRAILING);
		costLbl = new JLabel("Cost", JLabel.TRAILING);
		reportIntervalLbl = new JLabel("Report Interval", JLabel.TRAILING);
		remarksLbl = new JLabel("Remarks", JLabel.TRAILING);
		
		name = new JFormattedTextField();
		name.setColumns(20);
		name.addPropertyChangeListener("value", this);
		
		dateInstalledFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateInstalled = new JFormattedTextField(new DateFormatter(dateInstalledFormat));
		dateInstalled.setColumns(20);
		dateInstalled.setValue(new Date());
		dateInstalled.addPropertyChangeListener("value", this);
		
		costFormat = NumberFormat.getNumberInstance();
		costFormat.setMinimumFractionDigits(2);
		costFormat.setMaximumFractionDigits(2);
		costFormat.setGroupingUsed(true);
		cost = new JFormattedTextField(costFormat);
		cost.setColumns(20);
		cost.setValue(0);
		cost.addPropertyChangeListener("value", this);
		
		reportIntervalFormat = NumberFormat.getNumberInstance();
		reportIntervalFormat.setMinimumFractionDigits(0);
		reportIntervalFormat.setMaximumFractionDigits(0);
		reportIntervalFormat.setGroupingUsed(true);
		reportInterval = new JFormattedTextField(reportIntervalFormat);
		reportInterval.setColumns(20);
		reportInterval.setValue(0);
		reportInterval.addPropertyChangeListener("value", this);
		
		remarks = new JTextArea(5, 30);
		remarksScroll = new JScrollPane(remarks);
		
		nameLbl.setLabelFor(name);
		dateInstalledLbl.setLabelFor(dateInstalled);
		costLbl.setLabelFor(cost);
		reportIntervalLbl.setLabelFor(reportInterval);
		remarksLbl.setLabelFor(remarks);
		
		font = new Font("Arial", Font.BOLD, 16);
		nameLbl.setFont(font);
		dateInstalledLbl.setFont(font);
		costLbl.setFont(font);
		reportIntervalLbl.setFont(font);
		remarksLbl.setFont(font);
		
		font = new Font("Arial", Font.PLAIN, 16);
		name.setFont(font);
		dateInstalled.setFont(font);
		cost.setFont(font);
		reportInterval.setFont(font);
		remarks.setFont(font);
		
		formPanel = new JPanel(new SpringLayout());
		formPanel.add(nameLbl);
		formPanel.add(name);
		formPanel.add(dateInstalledLbl);
		formPanel.add(dateInstalled);
		formPanel.add(costLbl);
		formPanel.add(cost);
		formPanel.add(reportIntervalLbl);
		formPanel.add(reportInterval);
		formPanel.add(remarksLbl);
		formPanel.add(remarksScroll);
		SpringUtilities.makeCompactGrid(formPanel, 5, 2, 10, 20, 20, 20);
		add(BorderLayout.CENTER, formPanel);
		
		buttons = new JPanel();
		save = new JButton("Save");
		back = new JButton("Back");
		save.addActionListener(this);
		back.addActionListener(this);
		buttons.add(save);
		buttons.add(back);
		add(BorderLayout.SOUTH, buttons);
		
		pack();
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				new DeviceInfoTable();
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "Save":
				if (name.getText().isBlank()) {
					JOptionPane.showMessageDialog(this, "Invalid name!");
					break;
				}
				ArrayList<DeviceInfo> data = new ArrayList<>();

				DeviceInfo deviceInfo = new DeviceInfo(0, name.getText(),
						dateInstalled.getText(), 
						Double.valueOf(cost.getValue().toString()),
						Integer.valueOf(reportInterval.getValue().toString()), 
						remarks.getText());
				data.add(deviceInfo);
				
				DeviceInfoController.set_deviceInfo(data);
				this.dispose();
				new DeviceInfoTable();
				break;
			case "Clear Fields":
				break;
			case "Back":
				this.dispose();
				new DeviceInfoTable();
				break;
		}
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		
		if (source == cost) {
			try {
				double num = Double.parseDouble(cost.getValue().toString());
				if (num < 0.00) {
					JOptionPane.showMessageDialog(this, "Invalid negative input!");
					cost.setValue(lastValidCost);
				}
				else {
					lastValidCost = num;
				}
			}
			catch (Exception err) {
				JOptionPane.showMessageDialog(this, "Invalid negative input!");
				cost.setValue(lastValidCost);
			}
		}
		else if (source == reportInterval) {
			try {
				int num = Integer.parseInt(reportInterval.getValue().toString());
				if (num < 0) {
					JOptionPane.showMessageDialog(this, "Invalid negative input!");
					reportInterval.setValue(lastValidRi);
				}
				else {
					lastValidRi = Integer.parseInt(reportInterval.getValue().toString());
				}
			}
			catch (Exception err) {
				JOptionPane.showMessageDialog(this, "Invalid negative input!");
				reportInterval.setValue(lastValidRi);
			}
		}
	}
}
