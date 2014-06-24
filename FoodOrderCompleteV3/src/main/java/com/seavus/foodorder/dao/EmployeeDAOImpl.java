package com.seavus.foodorder.dao;

import java.util.List;

import org.hibernate.Query;

import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;

public class EmployeeDAOImpl extends GenericDAOImpl<Employee, Long> implements
		EmployeeDAO {

	@Override
	public Employee findByUsername(String username) {

		Employee employee = null;
		String sql = "FROM Employee e WHERE e.username = :username";
		Query query = (Query) HibernateUtil.getSession().createQuery(sql)
				.setParameter("username", username);
		employee = findOne(query);
		return employee;
	}

	@Override
	public boolean checkEmployee(String username, String password) {

		Employee employee = null;
		employee = findByUsername(username);
		if (employee != null) {
			if (employee.getUsername().equals(username)
					&& employee.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Employee getEmployeeForOrder(Order order) {
		String hql = "FROM Employee E WHERE E.id = :id";
		Query query = HibernateUtil.getSession().createQuery(hql)
				.setParameter("id", order.getEmployee().getId());
		List results = query.list();
		if (results.size() == 0) {
			return null;
		} else {
			return (Employee) results.get(0);
		}
	}
}
