package com.seavus.foodorder.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Restaurant;
import com.seavus.foodorder.service.EmployeeManagerImpl;
import com.seavus.foodorder.service.FoodManagerImpl;
import com.seavus.foodorder.service.OrderManagerImpl;
import com.seavus.foodorder.service.RestaurantManagerImpl;

public class AdminHomeMenuPanel {
	
	private JPanel contentPane;
	private JMenu homeMenu = new JMenu("Home");
	private JTabbedPane tabbedPane;
	private JPanel usersPanel;
	private JPanel restaurantsPanel;
	private JPanel foodsPanel;
	private JPanel ordersPanel;
	private EmployeeManagerImpl employeeManager;
	private RestaurantManagerImpl restaurantManager;
	private FoodManagerImpl foodManager;
	private OrderManagerImpl orderManager;
	
	public AdminHomeMenuPanel(JPanel contentPane, JMenuBar menuBar) {
		this.contentPane = contentPane;
		
		menuBar.add(homeMenu);
		initializeContentPane();
	}
	
	public void initializeContentPane() {
		contentPane.setLayout(new BorderLayout());
		contentPane.add(getTabbedPane());
		fillTabbedPane();	
		fillPanels();
	}	
	
	private void fillPanels() {
		fillUsersPanel();
		fillRestaurantsPanel();
		fillFoodsPanel();
		fillOrdersPanel();				
	}

	public void fillTabbedPane() {
		getTabbedPane().add("Users", getUsersPanel());
		getTabbedPane().add("Restaurants", getRestaurantsPanel());
		getTabbedPane().add("Foods", getFoodsPanel());
		getTabbedPane().add("Orders", getOrdersPanel());		
	}
	
	private String getAllOrderedFoodString(Order o) {
		String orderedFood = "";
		boolean first = true;
		List<Food> foods = foodManager.getFoodForOrder(o);
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
	
	private List<Order> getOrdersForToday() {
		Date today = new Date();
		List<Order> ordersForToday = new ArrayList<>();
		ordersForToday = getOrderManager().getOrdersForDate(today);
		return ordersForToday;
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

	
	private void fillOrdersPanel() {		
		List<Order> ordersForToday = getOrdersForToday();
		if(ordersForToday.size() != 0) {
		String[] columnNames = {"Employee","Ordered food","Note"};
		String[] orderRow;
		Object[][] ordersData = new Object[ordersForToday.size()][3];
		for(int i = 0; i < ordersForToday.size(); i++) {
			orderRow = new String[3];
			orderRow[0] = ordersForToday.get(i).getEmployee().getUsername();
			orderRow[1] = getAllOrderedFoodString(ordersForToday.get(i));
			orderRow[2] = ordersForToday.get(i).getNote();
			ordersData[i] = orderRow;
			orderRow = null;
		}		
		JTable ordersTable = new JTable(ordersData, columnNames);
		ordersTable.setFont(new Font("Arial", Font.PLAIN, 14));
		setColumnWidthsForOrdersTable(ordersTable);	
		JScrollPane scrollPane = new JScrollPane(ordersTable);
		ordersTable.setPreferredScrollableViewportSize(new Dimension(400, 70));	
		getOrdersPanel().add(scrollPane);
		}		
	}

	private void fillFoodsPanel() {			
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
