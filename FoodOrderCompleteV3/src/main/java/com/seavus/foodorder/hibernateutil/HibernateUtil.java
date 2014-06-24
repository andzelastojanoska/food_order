package com.seavus.foodorder.hibernateutil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.LocalisedFoodNames;
import com.seavus.foodorder.model.LocalisedFoodTypes;
import com.seavus.foodorder.model.LocalisedRestaurantNames;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.OrderedFood;
import com.seavus.foodorder.model.Rating;
import com.seavus.foodorder.model.Restaurant;
import com.seavus.foodorder.model.Role;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = new AnnotationConfiguration()
					.addAnnotatedClass(Employee.class)
					.addAnnotatedClass(Food.class)
					.addAnnotatedClass(Order.class)
					.addAnnotatedClass(Restaurant.class)
					.addAnnotatedClass(OrderedFood.class)
					.addAnnotatedClass(Rating.class)
					.addAnnotatedClass(Role.class)
					.addAnnotatedClass(LocalisedFoodNames.class)
					.addAnnotatedClass(LocalisedFoodTypes.class)
					.addAnnotatedClass(LocalisedRestaurantNames.class)
					.buildSessionFactory();
		}
		return sessionFactory;
	}

	public static Session beginTransaction() {
		Session hibernateSession = HibernateUtil.getSession();
		hibernateSession.beginTransaction();
		return hibernateSession;
	}

	public static void commitTransaction() {
		HibernateUtil.getSession().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		HibernateUtil.getSession().getTransaction().rollback();
	}

	public static void closeSession() {
		HibernateUtil.getSession().close();
	}

	public static Session getSession() {
		Session hibernateSession = sessionFactory.getCurrentSession();
		return hibernateSession;
	}
}
