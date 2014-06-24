package com.seavus.foodorder.service;

import java.util.Date;
import java.util.List;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;

public interface OrderManager {

	public void saveNewOrder(Order order);
	
	public List<Order> getOrdersForDate(Date date);
	
	public List<Order> getOrdersForEmployee(Employee employee);
	
}
