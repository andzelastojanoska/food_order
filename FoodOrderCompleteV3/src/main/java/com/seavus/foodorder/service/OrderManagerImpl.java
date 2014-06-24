package com.seavus.foodorder.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import com.seavus.foodorder.dao.OrderDAO;
import com.seavus.foodorder.dao.OrderDAOImpl;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;

public class OrderManagerImpl implements OrderManager {


	private OrderDAO orderDAO = new OrderDAOImpl();
	
	@Override
	public void saveNewOrder(Order order) {
		try {
            HibernateUtil.beginTransaction();
            orderDAO.save(order);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle error here");
            HibernateUtil.rollbackTransaction();
        }
	}

	@Override
	public List<Order> getOrdersForDate(Date date) {
		List<Order> allOrders = new ArrayList<>();
		try {
			HibernateUtil.beginTransaction();
			allOrders = orderDAO.getOrdersForDate(date);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Hangle error here");
		}
		return allOrders;
	}

	@Override
	public List<Order> getOrdersForEmployee(Employee employee) {
		List<Order> allOrders = new ArrayList<Order>();
		try {
			HibernateUtil.beginTransaction();
			allOrders = orderDAO.getOrdersForEmployee(employee);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Hangle error here");
		}
		return allOrders;
	}

}
