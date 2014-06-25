package com.seavus.foodorder.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Employee;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Restaurant;
import com.seavus.foodorder.model.Role;
import com.seavus.foodorder.service.RoleManagerImpl;

public class FillDatabase {	
	public static void fillEmployee() {
		RoleManagerImpl roleManager = new RoleManagerImpl();
		SessionFactory sessionFactory = HibernateUtil
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Role employeeRole = roleManager.getRole("Employee");
			Role adminRole = roleManager.getRole("Admin");
			Employee employee1 = new Employee("andzela", "stojanoska", "Andzela.Stojanoska@seavus.com", employeeRole);
			Employee employee2 = new Employee("martin", "pehcheski", "Martin.Pehcheski@seavus.com", employeeRole);
			Employee employee3 = new Employee("admin", "admin", "admin@admin.com", adminRole);
			session.save(employee1);
			session.save(employee2);
			session.save(employee3);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void fillRestaurant() {
		SessionFactory sessionFactory = HibernateUtil
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Restaurant restaurant1 = new Restaurant("7ca", "123456");
			restaurant1.setName("MK", "7ца");
			restaurant1.setName("EN", "7ca");
			Restaurant restaurant2 = new Restaurant("Kostarika", "456789");
			restaurant2.setName("MK", "Костарика");
			restaurant2.setName("EN", "Kostarika");
			Restaurant restaurant3 = new Restaurant("Royal Burger", "789123");
			restaurant3.setName("MK", "Ројал Бургер");
			restaurant3.setName("EN", "Royal Burger");
			session.save(restaurant1);
			session.save(restaurant2);
			session.save(restaurant3);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	public static void fillRole() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Role adminRole = new Role("Admin");
			Role employeeRole = new Role("Employee");
			session.save(adminRole);
			session.save(employeeRole);			
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public static void fillFood() {
		SessionFactory sessionFactory = HibernateUtil
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			
			Criteria cr1 = session.createCriteria(Restaurant.class);
			cr1.add(Restrictions.eq("name", "7ca"));
			List results1 = cr1.list();

			Restaurant restaurant1 = (Restaurant) results1.get(0);
//			Food food1 = new Food("Hamburger", "Burger", 1.0, restaurant1);
//			Food food2 = new Food("Cheeseburger", "Burger", 2.0, restaurant1);
//			Food food3 = new Food("Ceasar salad", "Salad", 3.0, restaurant1);
			
			Food food1 = new Food("Hamburger", "Burger", 1.0, restaurant1);
			food1.setName("EN", "Hamburger");
			food1.setName("MK", "Хамбургер");
			food1.setType("EN", "Burger");
			food1.setType("MK", "Бургер");
			
			Food food2 = new Food("Cheeseburger", "Burger", 2.0, restaurant1);
			food2.setName("EN", "Cheeseburger");
			food2.setName("MK", "Чизбургер");
			food2.setType("EN", "Burger");
			food2.setType("MK", "Бургер");
			
			Food food3 = new Food("Caesar salad", "Salad", 3.0, restaurant1);
			food3.setName("EN", "Caesar salad");
			food3.setName("MK", "Цезар салата");
			food3.setType("EN", "Salad");
			food3.setType("MK", "Салата");
			
			Set<Food> foods = new HashSet<>();
			foods.add(food1);
			foods.add(food2);
			foods.add(food3);
			restaurant1.setFoods(foods);
			
			Criteria cr2 = session.createCriteria(Restaurant.class);
			cr2.add(Restrictions.eq("name", "Kostarika"));
			List results2 = cr2.list();
			
			Restaurant restaurant2 = (Restaurant) results2.get(0);
//			Food food4 = new Food("Toast", "Burger", 1.0, restaurant2);
//			Food food5 = new Food("Kombiniran", "Burger", 2.0, restaurant2);
//			Food food6 = new Food("Chef salad", "Salad", 3.0, restaurant2);
			
			Food food4 = new Food("Tost", "Burger", 1.0, restaurant2);
			food4.setName("EN", "Tost");
			food4.setName("MK", "Тост");
			food4.setType("EN", "Burger");
			food4.setType("MK", "Бургер");
			
			Food food5 = new Food("Combinated", "Burger", 2.0, restaurant2);
			food5.setName("EN", "Combinated");
			food5.setName("MK", "Комбирниран");
			food5.setType("EN", "Burger");
			food5.setType("MK", "Бургер");
			
			Food food6 = new Food("Chef salad", "Salad", 3.0, restaurant2);
			food6.setName("EN", "Chef salad");
			food6.setName("MK", "Шеф салата");
			food6.setType("EN", "Salad");
			food6.setType("MK", "Салата");
			
			Set<Food> foods2 = new HashSet<>();
			foods.add(food4);
			foods.add(food5);
			foods.add(food6);
			restaurant2.setFoods(foods2);
			
			Criteria cr3 = session.createCriteria(Restaurant.class);
			cr3.add(Restrictions.eq("name", "Royal Burger"));
			List results3 = cr3.list();
			
			Restaurant restaurant3 = (Restaurant) results3.get(0);
//			Food food7 = new Food("Baconburger", "Burger", 1.0, restaurant3);
//			Food food8 = new Food("Vegeterian Sandwich", "Burger", 2.0, restaurant3);
//			Food food9 = new Food("Mixed salad", "Salad", 3.0, restaurant3);
			
			Food food7 = new Food("Baconburger", "Burger", 1.0, restaurant3);
			food7.setName("EN", "Baconburger");
			food7.setName("MK", "Бејкенбургер");
			food7.setType("EN", "Burger");
			food7.setType("MK", "Бургер");
			
			Food food8 = new Food("Vegetarian sandwich", "Burger", 2.0, restaurant3);
			food8.setName("EN", "Vegetarian sandwich");
			food8.setName("MK", "Вегетаријански сендвич");
			food8.setType("EN", "Burger");
			food8.setType("MK", "Бургер");
			
			Food food9 = new Food("Mixed salad", "Salad", 3.0, restaurant3);
			food9.setName("EN", "Mixed salad");
			food9.setName("MK", "Мешана салата");
			food9.setType("EN", "Salad");
			food9.setType("MK", "Салата");
			
			Set<Food> foods3 = new HashSet<>();
			foods.add(food7);
			foods.add(food8);
			foods.add(food9);
			restaurant3.setFoods(foods3);
			
			session.save(food1);
			session.save(food2);
			session.save(food3);
			session.save(food4);
			session.save(food5);
			session.save(food6);
			session.save(food7);
			session.save(food8);
			session.save(food9);
			
			session.save(restaurant1);
			session.save(restaurant2);
			session.save(restaurant3);
			
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
