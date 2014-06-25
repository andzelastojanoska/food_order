package com.seavus.foodorder.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

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
	private JButton order;

	private OrderManagerImpl orderManager = new OrderManagerImpl();
	private EmployeeManagerImpl employeeManager = new EmployeeManagerImpl();
	private FoodManagerImpl foodManager = new FoodManagerImpl();
	
	Locale locale = new Locale("mk", "MK"); //$NON-NLS-1$ //$NON-NLS-2$
	private ResourceBundle labels;

	public AllOrdersPanel(String username, ResourceBundle labels) {
		String className = getLookAndFeelClassName("Nimbus"); //$NON-NLS-1$
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setTitle(labels.getString("AllOrdersPanel.Title")); //$NON-NLS-1$
		this.username = username;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new FlowLayout());
		setContentPane(contentPane);

		createEmployeeOrdersPanel();
		createTodaysOrdersPanel();
		createOrderButton();
		contentPane.setVisible(true);

		order.addActionListener(new ActionListener() {

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
	
	public static String getLookAndFeelClassName(String nameSnippet) {
	    LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
	    for (LookAndFeelInfo info : plafs) {
	        if (info.getName().contains(nameSnippet)) {
	            return info.getClassName();
	        }
	    }
	    return null;
	}

	public void createOrderButton() {
		order = new JButton(labels.getString("AllOrdersPanel.BackToOrder")); //$NON-NLS-1$
		contentPane.add(order);
	}

	public void createEmployeeOrdersPanel() {
		JPanel employeeOrdersPanel = new JPanel();

		Employee employee = employeeManager.findByEmployeeUsername(username);
		List<Order> employeeOrders = orderManager.getOrdersForEmployee(employee);

		if (employeeOrders.size() == 0) {
			JLabel message = new JLabel(labels.getString("AllOrdersPanel.NothingOrdered")); //$NON-NLS-1$
			contentPane.add(message);
		} else {

			employeeOrdersPanel.setLayout(new GridLayout(employeeOrders.size() + 1, 1));

			JLabel text = new JLabel(labels.getString("AllOrdersPanel.YourOrders")); //$NON-NLS-1$
			employeeOrdersPanel.add(text);
			for (int i = 0; i < employeeOrders.size(); i++) {
				List<Food> orderedFood = foodManager.getFoodForOrder(employeeOrders.get(i));
				JLabel orderLabel = new JLabel();
				String food = ""; //$NON-NLS-1$
				for (int j = 0; j < orderedFood.size(); j++) {
					food += orderedFood.get(j).getName("MK") + " " + orderedFood.get(j).getPrice() + "; "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				String order = labels.getString("AllOrdersPanel.Date") + employeeOrders.get(i).getDate() + labels.getString("AllOrdersPanel.Food") + food + labels.getString("AllOrdersPanel.Total") + employeeOrders.get(i).getTotal(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				orderLabel.setText(order);
				employeeOrdersPanel.add(orderLabel);
			}
			contentPane.add(employeeOrdersPanel);
		}
	}

	public void createTodaysOrdersPanel() {

		List<Order> todaysOrders = orderManager.getOrdersForDate(new Date());

		if (todaysOrders.size() == 0) {
			JLabel message = new JLabel(labels.getString("AllOrdersPanel.NoOrdersForToday")); //$NON-NLS-1$
			contentPane.add(message);
		} else {

			JPanel todaysOrdersPanel = new JPanel();
			todaysOrdersPanel.setLayout(new GridLayout(todaysOrders.size() + 1, 1));
			JLabel text = new JLabel(labels.getString("AllOrdersPanel.TodaysOrders")); //$NON-NLS-1$
			todaysOrdersPanel.add(text);
			for (int i = 0; i < todaysOrders.size(); i++) {
				List<Food> orderedFood = foodManager
						.getFoodForOrder(todaysOrders.get(i));
				JLabel orderLabel = new JLabel();
				String food = ""; //$NON-NLS-1$
				for (int j = 0; j < orderedFood.size(); j++) {
					food += orderedFood.get(j).getName("MK") + " " //$NON-NLS-1$ //$NON-NLS-2$
							+ orderedFood.get(j).getPrice() + "; "; //$NON-NLS-1$
				}
				String order = labels.getString("AllOrdersPanel.Date1") + todaysOrders.get(i).getDate() //$NON-NLS-1$
						+ labels.getString("AllOrdersPanel.Food1") + food + labels.getString("AllOrdersPanel.Employee") + this.username; //$NON-NLS-1$ //$NON-NLS-2$
				orderLabel.setText(order);
				todaysOrdersPanel.add(orderLabel);
			}
			contentPane.add(todaysOrdersPanel);
		}
	}
}
