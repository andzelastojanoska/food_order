package com.seavus.foodorder.dao;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;

public interface EmployeeDAO extends GenericDAO<Employee, Long> {
	
	public Employee findByUsername(String username);
	
	public boolean checkEmployee(String username, String password);
	
	public Employee getEmployeeForOrder(Order order);
}
