package com.seavus.foodorder.service;

import java.util.List;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Role;

public interface EmployeeManager {
 
    public Employee findByEmployeeUsername(String username);
    
    public boolean checkEmployee(String username, String password);
 
    public List<Employee> loadAllEmployees();
 
    public void saveNewEmployee(Employee employee);
    
    public void updateEmployee(String username, String password, Employee employee);
    
    public void updateEmployee(String username, String password, String email, Role role, Employee employee);
 
    public Employee findEmployeeById(Long id);
 
    public void deleteEmployee(Employee employee);
    
    public Employee getEmployeeForOrder(Order order);

}
