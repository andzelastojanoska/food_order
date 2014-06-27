package com.seavus.foodorder.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.OrderedFood;
import com.seavus.foodorder.model.Restaurant;
import com.seavus.foodorder.service.EmployeeManagerImpl;
import com.seavus.foodorder.service.FoodManagerImpl;
import com.seavus.foodorder.service.OrderManagerImpl;
import com.seavus.foodorder.service.RestaurantManagerImpl;

public class AdminHomeMenuPanel {
	
	private JPanel contentPane;
	private JMenu homeMenu = new JMenu("Home");
	private JMenuItem homeListsMenuItem;
	private JTabbedPane tabbedPane;
	private JPanel usersPanel;
	private JPanel restaurantsPanel;
	private JPanel foodsPanel;
	private JPanel ordersPanel;
	private EmployeeManagerImpl employeeManager;
	private RestaurantManagerImpl restaurantManager;
	private FoodManagerImpl foodManager;
	private OrderManagerImpl orderManager;
	
	private Logger log;
	
	public AdminHomeMenuPanel(JPanel contentPane, JMenuBar menuBar) {
		this.contentPane = contentPane;
		initLogger();
		menuBar.add(homeMenu);
		initializeMenu();
		initializeContentPane();
		addActionListeners();
	}
	
	private void initLogger() {
		log = Logger.getLogger(AdminHomeMenuPanel.class.getName());
		Appender appender = Logger.getRootLogger().getAppender("UserFileAppender");
		log.removeAppender(appender);
	}
	
	private Logger getLogger() {
		if (log == null) {
			initLogger();
		}
		return log;
	}
	
	public void initializeContentPane() {
		contentPane.removeAll();
		repaint();
		contentPane.setLayout(new BorderLayout());		
		contentPane.add(getTabbedPane());
		fillTabbedPane();	
		fillPanels();
	}	
	
	private void initializeMenu() {
		homeListsMenuItem = new JMenuItem("Lists");
		homeMenu.add(homeListsMenuItem);	
	}
	
	private void fillPanels() {
		fillUsersPanel();
		fillRestaurantsPanel();
		fillFoodsPanel();
		fillOrdersPanel();				
	}

	public void fillTabbedPane() {
		getTabbedPane().add("Orders", getOrdersPanel());	
		getTabbedPane().add("Users", getUsersPanel());
		getTabbedPane().add("Restaurants", getRestaurantsPanel());
		getTabbedPane().add("Foods", getFoodsPanel());
			
	}
	
	private String getAllOrderedFoodString(Order o) {
		String orderedFood = "";
		boolean first = true;
		List<Food> foods = getFoodManager().getFoodForOrder(o);
		for(Food f : foods) {
			if(first) {
				orderedFood += f.getName();
				first = false;
			} else {
				orderedFood += ", ";
				orderedFood += f.getName();
			}
		}
		return orderedFood;
	}
	
	private List<Food> getAllFoods() {
		List<Restaurant> restaurants = getRestaurantManager().loadAllRestaurants();
		List<Food> foods = new ArrayList<>();
		for(int i = 0; i<restaurants.size(); i++) {
			List<Food> foodForRestaurant = getFoodManager().getFoodForRestaurant(restaurants.get(i));
			for(Food f : foodForRestaurant) {
				foods.add(f);
			}
		}		
		return foods;
	}
	
	private void setColumnWidthsForFoodsTable(JTable foodsTable) {
		TableColumn column = null;
		column = foodsTable.getColumnModel().getColumn(0);
	    column.setPreferredWidth(120);	
	    column = foodsTable.getColumnModel().getColumn(1);
	    column.setPreferredWidth(50);	
	    column = foodsTable.getColumnModel().getColumn(2);
	    column.setPreferredWidth(30);	
	}
	
	private void setColumnWidthsForOrdersTable(JTable ordersTable) {
		TableColumn column = null;
		column = ordersTable.getColumnModel().getColumn(0);
	    column.setPreferredWidth(50);	
	    column = ordersTable.getColumnModel().getColumn(1);
	    column.setPreferredWidth(120);	
	    column = ordersTable.getColumnModel().getColumn(2);
	    column.setPreferredWidth(30);	
	}
	
	private List<Order> getTodaysOrdersForRestaurant(Restaurant restaurant) {
		List<Order> todaysOrders = getOrderManager().getOrdersForDate(new Date());
		List<Order> todaysOrdersForRestaurant = new ArrayList<>();
		for(Order o : todaysOrders) {
			Set<OrderedFood> orderedFood = o.getOrderFoods();
			String restaurantName = "";
			for(OrderedFood of : orderedFood) {
				restaurantName = of.getFood().getRestaurant().getName();
				break;
			}
			if(restaurant.getName() == restaurantName) {
				todaysOrdersForRestaurant.add(o);
			}
		}
		return todaysOrdersForRestaurant;
	}
	
	private void fillOrdersPanel() {
		getOrdersPanel().removeAll();
		repaint();
		getOrdersPanel().setLayout(new FlowLayout());
		List<Restaurant> allRestaurants = getRestaurantManager().loadAllRestaurants();
		JScrollPane tablesScrollPane = null;
		JScrollPane scrollPane = null;
		for(Restaurant r : allRestaurants) {
			List<Order> todaysOrdersForRestaurant = getOrderManager().getTodaysOrdersForRestaurant(r);
			if(todaysOrdersForRestaurant.size() != 0) {
				String[] columnNames = {"Employee","Ordered food","Note","Restaurant"};
				String[] orderRow;
				Object[][] ordersData = new Object[todaysOrdersForRestaurant.size()][4];
				for(int i = 0; i < todaysOrdersForRestaurant.size(); i++) {
					orderRow = new String[4];
					orderRow[0] = todaysOrdersForRestaurant.get(i).getEmployee().getUsername();
					orderRow[1] = getAllOrderedFoodString(todaysOrdersForRestaurant.get(i));
					orderRow[2] = todaysOrdersForRestaurant.get(i).getNote();
					orderRow[3] = r.getName();
					ordersData[i] = orderRow;
					orderRow = null;
				}		
				JTable ordersTable = new JTable(ordersData, columnNames);
				ordersTable.setFont(new Font("Arial", Font.PLAIN, 14));
				setColumnWidthsForOrdersTable(ordersTable);	
				scrollPane = new JScrollPane(ordersTable);
				ordersTable.setPreferredScrollableViewportSize(new Dimension(400, 70));	
				getOrdersPanel().add(scrollPane);
				}
		//	tablesScrollPane.add(scrollPane);
		}
	}

	private void fillFoodsPanel() {		
		getFoodsPanel().removeAll();
		repaint();
		List<Food> foods = getAllFoods();
		String[] columnNames = {"Name","Type","Price","Restaurant"};
		String[] foodRow;
		Object[][] foodsData = new Object[foods.size()][4];
		for(int i = 0; i < foods.size(); i++) {			
				foodRow = new String[4];
				foodRow[0] = foods.get(i).getName();
				foodRow[1] = foods.get(i).getType();
				foodRow[2] = Double.toString(foods.get(i).getPrice());
				foodRow[3] = (restaurantManager.getRestaurantForFood(foods.get(i))).getName();
				foodsData[i] = foodRow;
				foodRow = null;								
		}		
		JTable foodsTable = new JTable(foodsData, columnNames);
		foodsTable.setFont(new Font("Arial", Font.PLAIN, 14));
		setColumnWidthsForFoodsTable(foodsTable);		
		JScrollPane scrollPane = new JScrollPane(foodsTable);
		foodsTable.setPreferredScrollableViewportSize(new Dimension(400, 70));	
		getFoodsPanel().add(scrollPane);
	}

	private void fillRestaurantsPanel() {
		getRestaurantsPanel().removeAll();
		repaint();
		List<Restaurant> restaurants = getRestaurantManager().loadAllRestaurants();
		String[] columnNames = {"Name","Phone number"};
		String[] restaurantRow;
		Object[][] restaurantsData = new Object[restaurants.size()][2];
		for(int i = 0; i < restaurants.size(); i++) {
			restaurantRow = new String[2];
			restaurantRow[0] = restaurants.get(i).getName();
			restaurantRow[1] = restaurants.get(i).getPhoneNumber();
			restaurantsData[i] = restaurantRow;
			restaurantRow = null;
		}
		
		JTable restaurantsTable = new JTable(restaurantsData, columnNames);
		restaurantsTable.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JScrollPane scrollPane = new JScrollPane(restaurantsTable);
		restaurantsTable.setPreferredScrollableViewportSize(new Dimension(300, 70));	
		
		getRestaurantsPanel().add(scrollPane);
	}

	private void fillUsersPanel() {
		getUsersPanel().removeAll();
		repaint();
		List<Employee> employees = getEmployeeManager().loadAllEmployees();
		String[] columnNames = {"Username","E-mail"};
		String[] employeeRow;
		Object[][] employeesData = new Object[employees.size()][2];
		for(int i = 0; i < employees.size(); i++) {
			employeeRow = new String [2];
			employeeRow[0] = employees.get(i).getUsername();
			employeeRow[1] = employees.get(i).getEmail();
			employeesData[i] = employeeRow;
			employeeRow = null;
		}
		JTable employeesTable = new JTable(employeesData, columnNames);
		employeesTable.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JScrollPane scrollPane = new JScrollPane(employeesTable);
		employeesTable.setPreferredScrollableViewportSize(new Dimension(300, 70));	
		
		getUsersPanel().add(scrollPane);
	}
	
	private void addActionListeners() {
		homeListsMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				initializeContentPane();				
			}
		});
	}
	
	
	
	public void repaint() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				contentPane.revalidate();
				contentPane.repaint();
			}
		});
	}
	
	private JTabbedPane getTabbedPane() {
		if(tabbedPane == null) {
			tabbedPane = new JTabbedPane();
		}
		return tabbedPane;
	}
	
	private JPanel getUsersPanel() {
		if(usersPanel == null) {
			usersPanel = new JPanel();
		}
		return usersPanel;
	}
	
	private JPanel getRestaurantsPanel() {
		if(restaurantsPanel == null) {
			restaurantsPanel = new JPanel();
		}
		return restaurantsPanel;
	}
	
	private JPanel getFoodsPanel() {
		if(foodsPanel == null) {
			foodsPanel = new JPanel();
		}
		return foodsPanel;
	}
	
	private JPanel getOrdersPanel() {
		if(ordersPanel == null) {
			ordersPanel = new JPanel();
		}
		return ordersPanel;
	}
	
	private EmployeeManagerImpl getEmployeeManager() {
		if(employeeManager == null) {
			employeeManager = new EmployeeManagerImpl();
		}
		return employeeManager;
	}

	private RestaurantManagerImpl getRestaurantManager() {
		if(restaurantManager == null) {
			restaurantManager = new RestaurantManagerImpl();
		}
		return restaurantManager;
	}
	
	private FoodManagerImpl getFoodManager() {
		if(foodManager == null) {
			foodManager = new FoodManagerImpl();
		}
		return foodManager;
	}
	
	private OrderManagerImpl getOrderManager() {
		if(this.orderManager == null) {
			orderManager = new OrderManagerImpl();
		}
		return orderManager;
	}
}
