package com.seavus.foodorder.service;

import java.util.Date;
import java.util.List;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Restaurant;

public interface OrderManager {

	public void saveNewOrder(Order order);
	
	public List<Order> getOrdersForDate(Date date);
	
	public List<Order> getOrdersForEmployee(Employee employee);
	
	public List<Order> getTodaysOrdersForRestaurant(Restaurant restaurant);
	
}
