import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.*;
import java.util.*;

public class DeviceInfoForm extends JFrame implements ActionListener, PropertyChangeListener {
	// Initialize frame components.
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
	
	// The last valid value of the date installed field.
	private String lastValidDi = "";
	// The last valid value of the cost field.
	private double lastValidCost = 0;
	// The last valid value of the report interval field.
	private int lastValidRi = 0;
	// Specifies whether if the method is either "ADD" or "EDIT".
	private String operation;
	// If operation is "EDIT", this gets the id of the row to be edited.
	private int id = 0;
	
	/**
	 * A frame where the user enters a data to be added or updated in device_info.
	 * @param method is a String that specifies if the frame will "ADD" or "EDIT" data.
	 * @param rowData is a Vector that contains the data to be edited.
	 */
	public DeviceInfoForm(String method, Vector rowData) {
		// Set up frame.
		super(method + " GPS Device Info");
		setResizable(false);
		setLocationRelativeTo(null);
		
		// Set up labels.
		nameLbl = new JLabel("Name", JLabel.TRAILING);
		dateInstalledLbl = new JLabel("Date Installed", JLabel.TRAILING);
		costLbl = new JLabel("Cost", JLabel.TRAILING);
		reportIntervalLbl = new JLabel("Report Interval", JLabel.TRAILING);
		remarksLbl = new JLabel("Remarks", JLabel.TRAILING);
		
		// Set up name field.
		name = new JFormattedTextField();
		name.setColumns(20);
		name.addPropertyChangeListener("value", this);
		
		// Set up date installed field.
		// Set the format to be "[YEAR]-[MONTH]-[DAY] [HOUR]:[MINUTE]:[SECOND]"
		dateInstalledFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateInstalled = new JFormattedTextField(new DateFormatter(dateInstalledFormat));
		dateInstalled.setColumns(20);
		dateInstalled.addPropertyChangeListener("value", this);
		
		// Set up cost field.
		costFormat = NumberFormat.getNumberInstance();
		// Set the number to show 2 decimal places.
		costFormat.setMinimumFractionDigits(2);
		// Set the number to be only up to 2 decimal places.
		costFormat.setMaximumFractionDigits(2);
		// Allow grouping of numbers through comma (###,###,###).
		costFormat.setGroupingUsed(true);
		cost = new JFormattedTextField(costFormat);
		cost.setColumns(20);
		cost.addPropertyChangeListener("value", this);
		
		// Set up report interval field.
		reportIntervalFormat = NumberFormat.getNumberInstance();
		// Set the number to have no decimal places.
		reportIntervalFormat.setMinimumFractionDigits(0);
		reportIntervalFormat.setMaximumFractionDigits(0);
		// Allow grouping of numbers through comma (###,###,###).
		reportIntervalFormat.setGroupingUsed(true);
		reportInterval = new JFormattedTextField(reportIntervalFormat);
		reportInterval.setColumns(20);
		reportInterval.addPropertyChangeListener("value", this);
		
		// Set up remarks field.
		remarks = new JTextArea(5, 30);
		remarksScroll = new JScrollPane(remarks);
		
		// Set labels to their corresponding fields.
		nameLbl.setLabelFor(name);
		dateInstalledLbl.setLabelFor(dateInstalled);
		costLbl.setLabelFor(cost);
		reportIntervalLbl.setLabelFor(reportInterval);
		remarksLbl.setLabelFor(remarks);
		
		// Set font for the labels.
		font = new Font("Arial", Font.BOLD, 16);
		nameLbl.setFont(font);
		dateInstalledLbl.setFont(font);
		costLbl.setFont(font);
		reportIntervalLbl.setFont(font);
		remarksLbl.setFont(font);
		
		// Set font for the fields.
		font = new Font("Arial", Font.PLAIN, 16);
		name.setFont(font);
		dateInstalled.setFont(font);
		cost.setFont(font);
		reportInterval.setFont(font);
		remarks.setFont(font);
		
		// Add the labels and fields to a panel, and add the panel to the frame.
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
		
		// Set up buttons
		// Set the corresponding button text if the frame would save or update data.
		if (method == "Add") {
			save = new JButton("Save");
		}
		else if (method == "Edit") {
			save = new JButton("Update");
		}
		back = new JButton("Back");
		save.addActionListener(this);
		back.addActionListener(this);
		
		// Add buttons to a panel, and add the panel to the frame.
		buttons = new JPanel();
		buttons.add(save);
		buttons.add(back);
		add(BorderLayout.SOUTH, buttons);
		
		// Make the frame compact and make it visible.
		pack();
		setVisible(true);
		
		// Set the operation according to the method;
		operation = method;
		
		// Initialize the value of fields depending on the operation.
		if (operation == "Add") {
			dateInstalled.setValue(new Date());
			lastValidDi = dateInstalled.getText();
			cost.setValue(0);
			reportInterval.setValue(0);
		}
		else if (operation == "Edit") {
			name.setText(rowData.get(1).toString());
			lastValidDi = rowData.get(2).toString();
			dateInstalled.setText(lastValidDi);
			lastValidCost = Double.valueOf(rowData.get(3).toString());
			cost.setValue(lastValidCost);
			lastValidRi = Integer.valueOf(rowData.get(4).toString());
			reportInterval.setValue(lastValidRi);
			remarks.setText(rowData.get(5).toString());
			
			// Get the id of the row to be updated.
			id = Integer.valueOf(rowData.get(0).toString());
		}
		
		// Upon closing this frame, the device info table frame would be called.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				new DeviceInfoTable();
			}
		});
	}
	
	/**
	 * Listener of the performed actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Does actions depending on the source of action.
		switch (e.getActionCommand()) {
			// Does the following when "SAVE" or "UPDATE" button is pressed.
			case "Save":
			case "Update":	
				// Trigger a pop up if name is empty and cancel the saving or updating.
				if (name.getText().isBlank()) {
					JOptionPane.showMessageDialog(this, "Invalid name.");
					break;
				}

				// Get the value of fields.
				DeviceInfo deviceInfo = new DeviceInfo(id, name.getText(),
						dateInstalled.getText(), 
						Double.valueOf(cost.getValue().toString()),
						Integer.valueOf(reportInterval.getValue().toString()), 
						remarks.getText());
				
				// Call the necessary controller actions depending on the operation.
				if (operation == "Add") {
					DeviceInfoController.set_deviceInfo(deviceInfo);
				}
				else if (operation == "Edit") {
					DeviceInfoController.update_deviceInfo(deviceInfo, id);
				}
				
				// Go back to device info table frame.
				this.dispose();
				new DeviceInfoTable();
				break;
			case "Back":
				// Go back to device info table frame.
				this.dispose();
				new DeviceInfoTable();
				break;
		}
	}
	
	/**
	 * Listener of changes of value in fields.
	 */
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
		
		// Date installed value changes
		if (source == dateInstalled) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			// If new value is not valid date, show a pop-up and revert the field value 
			// to the last valid value.
			try {
				String date = sdf.format(java.sql.Timestamp.valueOf(dateInstalled.getText()));
				
				// If new value is valid, make it the last valid value.
				lastValidDi = dateInstalled.getText();
			}
			// If new value is invalid date, show a pop-up and revert the field value
			// to the last valid value.
			catch (Exception err) {
				JOptionPane.showMessageDialog(this, 
						"Please enter date of format [yyyy-MM-dd HH:mm:ss].");
				dateInstalled.setValue(java.sql.Timestamp.valueOf(lastValidDi));
			}
		}
		// Source value changes
		else if (source == cost) {
			try {
				double num = Double.parseDouble(cost.getValue().toString());
				// If new value is negative, show a pop-up and revert the field value to the
				// last valid value.
				if (num < 0.00) {
					JOptionPane.showMessageDialog(this, "Non-negative numbers only.");
					cost.setValue(lastValidCost);
				}
				// If new value is valid, make it the last valid value.
				else {
					lastValidCost = num;
				}
			}
			// If new value has non-numerical characters, show a pop-up and revert the field
			// value to the last valid value.
			catch (Exception err) {
				JOptionPane.showMessageDialog(this, "Numerical characters only.");
				cost.setValue(lastValidCost);
			}
		}
		// Report interval value changes
		else if (source == reportInterval) {
			try {
				int num = Integer.parseInt(reportInterval.getValue().toString());
				// If new value is negative, show a pop-up and revert the field value to the
				// last valid value.
				if (num < 0) {
					JOptionPane.showMessageDialog(this, "Non-negative numbers only.");
					reportInterval.setValue(lastValidRi);
				}
				// If new value is valid, make it the last valid value.
				else {
					lastValidRi = Integer.parseInt(reportInterval.getValue().toString());
				}
			}
			// If new value has non-numerical characters, show a pop-up and revert the field
			// value to the last valid value.
			catch (Exception err) {
				JOptionPane.showMessageDialog(this, "Whole number and numerical characters only.");
				reportInterval.setValue(lastValidRi);
			}
		}
	}
}
