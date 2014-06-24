package com.seavus.foodorder.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.hibernate.HibernateException;

import com.seavus.foodorder.dao.EmployeeDAO;
import com.seavus.foodorder.dao.EmployeeDAOImpl;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Role;

public class EmployeeManagerImpl implements EmployeeManager {

	private EmployeeDAO employeeDAO = new EmployeeDAOImpl();

	@Override
	public Employee findByEmployeeUsername(String username) {
		Employee employee = null;
		try {
			HibernateUtil.beginTransaction();
			employee = employeeDAO.findByUsername(username);
			HibernateUtil.commitTransaction();
		} catch (NonUniqueResultException ex) {
			System.out.println("Query returned more than one result.");
		} catch (HibernateException ex) {
			// Error handling code
		}
		return employee;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> loadAllEmployees() {
		List<Employee> allEmployees = new ArrayList<Employee>();
		try {
			allEmployees = employeeDAO.findAll(Employee.class);
		} catch (HibernateException ex) {
			System.out.println("Hangle error here");
		}
		return allEmployees;
	}

	@Override
	public void saveNewEmployee(Employee employee) {

		try {
			HibernateUtil.beginTransaction();
			employeeDAO.save(employee);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public Employee findEmployeeById(Long id) {
		Employee employee = null;
		try {
			HibernateUtil.beginTransaction();
			employee = (Employee) employeeDAO.findByID(Employee.class, id);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
		}
		return employee;
	}

	@Override
	public void deleteEmployee(Employee employee) {
		try {
			HibernateUtil.beginTransaction();
			employeeDAO.delete(employee);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
			HibernateUtil.rollbackTransaction();
		}
	}

	@Override
	public boolean checkEmployee(String username, String password) {
		boolean result = false;
		try {
			HibernateUtil.beginTransaction();
			result = employeeDAO.checkEmployee(username, password);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			// Error handling code
		}
		return result;
	}

	@Override
	public Employee getEmployeeForOrder(Order order) {
		Employee employee = null;
		try {
			HibernateUtil.beginTransaction();
			employee = employeeDAO.getEmployeeForOrder(order);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			// error handling
		}
		return employee;
	}

	@Override
	public void updateEmployee(String username, String password,
			Employee employee) {

		employee.setUsername(username);
		employee.setPassword(password);
		try {
			HibernateUtil.beginTransaction();
			employeeDAO.merge(employee);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			// handle exception
		}

	}

	@Override
	public void updateEmployee(String username, String password, String email, Role role, Employee employee) {

		employee.setUsername(username);
		employee.setPassword(password);
		employee.setEmail(email);
		employee.setRole(role);
		try {
			HibernateUtil.beginTransaction();
			employeeDAO.merge(employee);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			// handle exception
		}
	}

}
