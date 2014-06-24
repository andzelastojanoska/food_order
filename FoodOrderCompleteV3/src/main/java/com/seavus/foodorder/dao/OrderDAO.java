package com.seavus.foodorder.dao;

import java.util.Date;
import java.util.List;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;

public interface OrderDAO extends GenericDAO<Order, Long> {

	public List<Order> getOrdersForDate(Date date);
	
	public List<Order> getOrdersForEmployee(Employee employee);
	
}
