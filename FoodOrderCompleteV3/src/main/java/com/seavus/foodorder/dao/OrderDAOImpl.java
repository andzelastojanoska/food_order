package com.seavus.foodorder.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Restaurant;

public class OrderDAOImpl extends GenericDAOImpl<Order, Long> implements OrderDAO {

	@Override
	public List<Order> getOrdersForDate(Date date) {
		List<Order> orders = new ArrayList<>();
        String sql = "FROM Order o WHERE o.date = :date";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("date", date);
        orders = (ArrayList<Order>) findMany(query);
        return orders;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersForEmployee(Employee employee) {
		ArrayList<Order> orders = null;
        String sql = "FROM Order o WHERE o.employee.id = :employee_id";
        Query query = HibernateUtil.getSession().createQuery(sql).setParameter("employee_id", employee.getId());
        orders = (ArrayList<Order>) query.list();
        if (orders.size() == 0) {
			return null;
		} else {
			return orders;
		}
	}

	@Override
	public List<Order> getTodaysOrdersForRestaurant(Restaurant restaurant) {
		List<Order> orders = new ArrayList<>();
		Date date = new Date();
		String hql = "FROM Order o left join o.orderedFoods as of where of.food.id = " +
				"(Select rf.id From Restaurant r left join r.foods as rf)" +
				"and o.date = :date" +
				"and r.id = :restaurant_id";
		Query query = HibernateUtil.getSession().createQuery(hql).setParameter("date", date).setParameter("restaurant_id", restaurant.getId());
		orders = findMany(query);
		return orders;
	}

}
