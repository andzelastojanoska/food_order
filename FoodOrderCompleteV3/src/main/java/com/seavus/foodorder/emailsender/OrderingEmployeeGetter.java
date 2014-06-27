package com.seavus.foodorder.emailsender;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Restaurant;
import com.seavus.foodorder.service.FoodManagerImpl;
import com.seavus.foodorder.service.OrderManagerImpl;
import com.seavus.foodorder.service.RestaurantManagerImpl;

public class OrderingEmployeeGetter implements Job {

	private RestaurantManagerImpl restaurantManager;
	private OrderManagerImpl orderManager;
	private FoodManagerImpl foodManager;
	private Double totalPrice;
	private Double totalRestaurantPrice;
	
	private Random randomGenerator;

	public void sendMail(String employeeMail) {
		EmailSender mailSender = new PlainTextEmailSender();
		// SMTP server information
		String host = "smtp.gmail.com";
		String port = "587";
		String mailFrom = "foodorder12345@gmail.com";
		String password = "foodorderpassword";

		// outgoing message information
		String mailTo = employeeMail;
		String subject = "Food Order Notification";
		String message = "Today it's your turn to make the orders.\n\n" + setMailText()  + "\nKind regards, \nTheFoodOrderApp.";

		try {
			mailSender.sendPlainTextEmail(host, port, mailFrom, password,
					mailTo, subject, message);
			System.out.println("Email sent.");
		} catch (Exception ex) {
			System.out.println("Failed to sent email.");
			ex.printStackTrace();
		}
	}
	
	private String getAllOrderedFoodString(Order o) {
		String orderedFood = "";
		boolean first = true;
		List<Food> foods = getFoodManager().getFoodForOrder(o);
		for(Food f : foods) {
			if(first) {
				orderedFood += f.getName() + " - " + f.getPrice("MK") + " MKD";
				addToTotalPrice(f.getPrice("MK"));
				first = false;
			} else {
				orderedFood += ", ";
				orderedFood += f.getName();
			}
		}
		addToTotalRestaurantPrice(getTotalPrice());		
		return orderedFood;
	}
	
	private Random getRandom() {
		if (randomGenerator == null) {
			randomGenerator = new Random();
		}
		return randomGenerator;
	}

	private Employee unluckyEmployee(List<Employee> employees) {
		int index = getRandom().nextInt(employees.size());
		Employee employee = employees.get(index);
		return employee;
	}

	private List<Employee> getEmployeesWhoOrderedFood() {
		List<Employee> employees = new ArrayList<>();
		List<Order> ordersForToday = getOrderManager().getOrdersForDate(new Date());
		for(Order o : ordersForToday) {
			employees.add(o.getEmployee());
		}		
		return employees;
	}
	
	private String setMailText() {
		List<Restaurant> allRestaurants = getRestaurantManager().loadAllRestaurants();
		String mailText = "";
		for (Restaurant r : allRestaurants) {
			List<Order> todaysOrdersForRestaurant = getOrderManager().getTodaysOrdersForRestaurant(r);
			if (todaysOrdersForRestaurant.size() != 0) {				
				for (int i = 0; i < todaysOrdersForRestaurant.size(); i++) {
					mailText += "Employee: " + todaysOrdersForRestaurant.get(i).getEmployee().getUsername() + " / ";
					mailText += "Ordered food: " + getAllOrderedFoodString(todaysOrdersForRestaurant.get(i)) + " / ";					
					mailText += "Total order price: " + getTotalPrice() + " MKD / ";
					reinitializeTotalPrice();
					if(todaysOrdersForRestaurant.get(i).getNote() != null) {
						mailText += "Note: " + todaysOrdersForRestaurant.get(i).getNote() + " / ";
					}
					mailText += "Restaurant: " + r.getName() + " (" + r.getPhoneNumber() + ")\n";
				}
				mailText += "Total: " + getTotalRestaurantPrice() + " MKD\n\n";
				setTotalRestaurantPrice(Double.valueOf(0));
			}			
		}		
		return mailText;
	}

	private OrderManagerImpl getOrderManager() {
		if (orderManager == null) {
			orderManager = new OrderManagerImpl();
		}
		return orderManager;
	}

	private RestaurantManagerImpl getRestaurantManager() {
		if (restaurantManager == null) {
			restaurantManager = new RestaurantManagerImpl();
		}
		return restaurantManager;
	}
	
	private FoodManagerImpl getFoodManager() {
		if (foodManager == null) {
			foodManager = new FoodManagerImpl();
		}
		return foodManager;
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		System.out.println("mail sent unlucky");
		sendMail(unluckyEmployee(getEmployeesWhoOrderedFood()).getEmail());
		
	}
	
	private Double getTotalPrice() {
		if(totalPrice == null) {
			totalPrice = new Double(0);
		}
		return totalPrice;
	}
	
	private void setTotalPrice(Double price) {
		this.totalPrice = price;
	}
	
	private void addToTotalPrice(double value) {
		setTotalPrice(getTotalPrice() + Double.valueOf(value));
	}

	private void reinitializeTotalPrice() {
		setTotalPrice(Double.valueOf(0));
	}
	
	private Double getTotalRestaurantPrice() {
		if(totalRestaurantPrice == null) {
			totalRestaurantPrice = new Double(0);
		}
		return totalRestaurantPrice;
	}
	
	private void setTotalRestaurantPrice(Double price) {
		this.totalRestaurantPrice = price;
	}
	
	private void addToTotalRestaurantPrice(double value) {
		setTotalRestaurantPrice(getTotalRestaurantPrice() + Double.valueOf(value));
	}
	
	
}
