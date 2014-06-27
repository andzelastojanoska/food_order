package com.seavus.foodorder.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.service.EmployeeManagerImpl;
import com.seavus.foodorder.service.FoodManagerImpl;
import com.seavus.foodorder.service.OrderManagerImpl;

@SuppressWarnings("serial")
public class AllOrdersPanel extends JFrame {

	private String username;

	private JPanel contentPane;
	private JButton orderButton;
	private JPanel todaysOrdersPanel;
	private JPanel employeesOrdersPanel;
	private JLabel todaysOrdersLabel;
	private JLabel employeesOrdersLabel;

	private OrderManagerImpl orderManager;
	private EmployeeManagerImpl employeeManager;
	private FoodManagerImpl foodManager;
	
	private Locale locale;
	private ResourceBundle labels;
	private Logger log;
	
	private GridBagConstraints gbc;

	public AllOrdersPanel(String username, Locale locale) {		
		this.username = username;
		this.locale = locale;

		initializeAllOrdersPanel();	
	}
	
	public static String getLookAndFeelClassName(String nameSnippet) {
	    LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
	    for (LookAndFeelInfo info : plafs) {
	        if (info.getName().contains(nameSnippet)) {
	            return info.getClassName();
	        }
	    }
	    return null;
	}

	private void setLookAndFeel() {
		String className = getLookAndFeelClassName("Nimbus"); //$NON-NLS-1$
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}
	
	private void initializeAllOrdersPanel() {
		initLogger();
		setLookAndFeel();
		setResourceBundle();
		setTitle(labels.getString("AllOrdersPanel.Title"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);
		fillContentPane();
		contentPane.setVisible(true);		
		addActionListeners();
	}
	
	private void addActionListeners() {
		getOrderButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
	
	private void initLogger() {
		log = Logger.getLogger(AllOrdersPanel.class.getName());
		Appender appender = Logger.getRootLogger().getAppender("AdminFileAppender");
		log.removeAppender(appender);
	}
	
	private Logger getLogger() {
		if (log == null) {
			initLogger();
		}
		return log;
	}
	
	private void fillContentPane() {	
		fillEmployeeOrdersPanel();
		fillTodaysOrdersPanel();
		
		getGBC().gridx = 0;
		getGBC().gridy = 0;
		getGBC().fill = GridBagConstraints.HORIZONTAL;
		getGBC().gridwidth = 1;
		todaysOrdersLabel = new JLabel(labels.getString("AllOrdersPanel.TodaysOrders"));
		todaysOrdersLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(todaysOrdersLabel, getGBC());		
		
		getGBC().gridy = 1;
		contentPane.add(getTodaysOrdersPanel(), getGBC());
				
		getGBC().gridy = 2;
		employeesOrdersLabel = new JLabel(labels.getString("AllOrdersPanel.YourOrders"));
		employeesOrdersLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(employeesOrdersLabel, getGBC());
		
		getGBC().gridy = 3;
		contentPane.add(getEmployeesOrdersPanel(), getGBC());
		
		getGBC().gridy = 4;
		getGBC().insets = new Insets(10, 50, 10, 50);
		contentPane.add(getOrderButton(), getGBC());
		getGBC().insets = new Insets(0, 0, 0, 0);
	}
	
	private JButton getOrderButton() {
		if(orderButton == null) {
			orderButton = new JButton(labels.getString("AllOrdersPanel.BackToOrder"));
		}		
		return orderButton;
	}
	
	public void fillEmployeeOrdersPanel() {
		Employee employee = getEmployeeManager().findByEmployeeUsername(username);
		List<Order> employeeOrders = new ArrayList<>();
		employeeOrders = getOrderManager().getOrdersForEmployee(employee);
		getEmployeesOrdersPanel().setLayout(new GridBagLayout());
		getGBC().insets = new Insets(10, 10, 10, 10);

		if (employeeOrders == null) {
			JLabel message = new JLabel(labels.getString("AllOrdersPanel.NothingOrdered")); //$NON-NLS-1$
			getGBC().gridx = 0;
			getGBC().gridy = 0;
			getEmployeesOrdersPanel().add(message, getGBC());
		} else {
			String[] columnNames = {labels.getString("AllOrdersPanel.Date"),labels.getString("AllOrdersPanel.Food"),labels.getString("AllOrdersPanel.Total")};
			String[] orderRow;
			Object[][] orderData = new Object[employeeOrders.size()][3];
			
			for(int j = 0; j < employeeOrders.size(); j++) {
				orderRow = new String [3];
				orderRow[0] = employeeOrders.get(j).getDate().toString();
				
				List<Food> orderedFood = getFoodManager().getFoodForOrder(employeeOrders.get(j));
				String food = "";
				for (int i = 0; i < orderedFood.size(); i++) {
					food += orderedFood.get(i).getName(getLocale().getCountry()) + "; ";
				}	
				
				orderRow[1] = food;
				orderRow[2] = Double.toString(employeeOrders.get(j).getTotal());
				orderData[j] = orderRow;
				orderRow = null;
			}
			JTable employeeOrdersTable = new JTable(orderData, columnNames);
			employeeOrdersTable.setFont(new Font("Arial", Font.PLAIN, 14));
			setColumnWidthsForFoodsTable(employeeOrdersTable);
			
			JScrollPane scrollPane = new JScrollPane(employeeOrdersTable);
			employeeOrdersTable.setPreferredScrollableViewportSize(new Dimension(400, 70));	
							
			getGBC().gridx = 0;
			getGBC().gridy = 0;
			getGBC().fill = GridBagConstraints.HORIZONTAL;
			getGBC().gridwidth = 1;
			getEmployeesOrdersPanel().add(scrollPane);
		}
		getGBC().insets = new Insets(0, 0, 0, 0);
	}
	
	private void setColumnWidthsForFoodsTable(JTable foodsTable) {
		TableColumn column = null;
		column = foodsTable.getColumnModel().getColumn(0);
	    column.setPreferredWidth(100);	
	    column = foodsTable.getColumnModel().getColumn(1);
	    column.setPreferredWidth(200);	
	    column = foodsTable.getColumnModel().getColumn(2);
	    column.setPreferredWidth(100);	
	}

	public void fillTodaysOrdersPanel() {
		List<Order> todaysOrders = getOrderManager().getOrdersForDate(new Date());
		getTodaysOrdersPanel().setLayout(new GridBagLayout());
		
		getGBC().insets = new Insets(10, 10, 10, 10);

		if (todaysOrders.size() == 0) {
			JLabel message = new JLabel(labels.getString("AllOrdersPanel.NoOrdersForToday")); //$NON-NLS-1$
			getGBC().gridx = 0;
			getGBC().gridy = 0;
			getTodaysOrdersPanel().add(message, getGBC());
		} else {
			String[] columnNames = {labels.getString("AllOrdersPanel.Date1"),labels.getString("AllOrdersPanel.Food1"),labels.getString("AllOrdersPanel.Employee")};
			String[] todayRow;
			Object[][] todayData = new Object[todaysOrders.size()][3];			
			
			for (int i = 0; i < todaysOrders.size(); i++) {
				todayRow = new String [3];
				todayRow[0] = todaysOrders.get(i).getDate().toString();
				
				List<Food> orderedFood = getFoodManager().getFoodForOrder(todaysOrders.get(i));				
				String food = ""; 
				for (int j = 0; j < orderedFood.size(); j++) {
					food += orderedFood.get(j).getName(getLocale().getCountry()) + "; "; 
				}
				
				todayRow[1] = food;
				todayRow[2] = todaysOrders.get(i).getEmployee().getUsername();
				todayData[i] = todayRow;
				todayRow = null;
			}
			
			JTable todaysOrdersTable = new JTable(todayData, columnNames);
			todaysOrdersTable.setFont(new Font("Arial", Font.PLAIN, 14));
			setColumnWidthsForFoodsTable(todaysOrdersTable);
			
			JScrollPane scrollPane = new JScrollPane(todaysOrdersTable);
			todaysOrdersTable.setPreferredScrollableViewportSize(new Dimension(400, 70));				
							
			getGBC().gridx = 0;
			getGBC().gridy = 0;
			getGBC().fill = GridBagConstraints.HORIZONTAL;
			getGBC().gridwidth = 1;
			getTodaysOrdersPanel().add(scrollPane);
		}
		getGBC().insets = new Insets(0, 0, 0, 0);
	}

	private JPanel getTodaysOrdersPanel() {
		if(todaysOrdersPanel == null) {
			todaysOrdersPanel = new JPanel();
		}
		return todaysOrdersPanel;
	}
	

	private JPanel getEmployeesOrdersPanel() {
		if(employeesOrdersPanel == null) {
			employeesOrdersPanel = new JPanel();
		}
		return employeesOrdersPanel;
	}	
	
	
	private void setResourceBundle() {
		this.labels = ResourceBundle.getBundle("com.seavus.foodorder.i18n.AllOrdersPanelMessages", getLocale());
	}
	
	
	public Locale getLocale() {
		return this.locale;
	}
	
	
	private EmployeeManagerImpl getEmployeeManager() {
		if(employeeManager == null) {
			employeeManager = new EmployeeManagerImpl();
		}
		return employeeManager;
	}

	private OrderManagerImpl getOrderManager() {
		if(orderManager == null) {
			orderManager = new OrderManagerImpl();
		}
		return orderManager;
	}
	
	
	private FoodManagerImpl getFoodManager() {
		if(foodManager == null) {
			foodManager = new FoodManagerImpl();
		}
		return foodManager;
	}

	private GridBagConstraints getGBC() {
		if(gbc == null) {
			gbc = new GridBagConstraints();
		}
		return gbc;
	}
	
}
