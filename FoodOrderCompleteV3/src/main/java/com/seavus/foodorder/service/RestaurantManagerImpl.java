package com.seavus.foodorder.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import com.seavus.foodorder.dao.RestaurantDAO;
import com.seavus.foodorder.dao.RestaurantDAOImpl;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Restaurant;

public class RestaurantManagerImpl implements RestaurantManager {

	private RestaurantDAO restaurantDAO = new RestaurantDAOImpl();

	@SuppressWarnings("unchecked")
	@Override
	public List<Restaurant> loadAllRestaurants() {
		List<Restaurant> allRestaurants = new ArrayList<Restaurant>();
		try {
			allRestaurants = restaurantDAO.findAll(Restaurant.class);
		} catch (HibernateException ex) {
			System.out.println("Hangle error here");
		}
		return allRestaurants;
	}

	@Override
	public Restaurant getRestaurantObjectForName(String name, String locale) {
		Restaurant restaurant = restaurantDAO.getRestaurantObjectForName(name, locale);
		return restaurant;
	}

	@Override
	public void addRestaurant(Restaurant restaurant) {
		try {
			HibernateUtil.beginTransaction();
			restaurantDAO.save(restaurant);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
			HibernateUtil.rollbackTransaction();
		}

	}

	@Override
	public void updateRestaurant(String name, String phoneNumber,
			Restaurant restaurant) {
		restaurant.setName(name);
		restaurant.setPhoneNumber(phoneNumber);
		try {
			HibernateUtil.beginTransaction();
			restaurantDAO.merge(restaurant);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			// handle exception
		}

	}
	
	@Override
	public void updateRestaurant(String name, String phoneNumber, String language, String localisedName, Restaurant restaurant) {
		
		restaurant.setName(name);
		restaurant.setPhoneNumber(phoneNumber);
		restaurant.setName(language, localisedName);
		try {
			HibernateUtil.beginTransaction();
			restaurantDAO.merge(restaurant);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			// handle exception
		}
	}

	@Override
	public void deleteRestaurant(Restaurant restaurant) {
		try {
			HibernateUtil.beginTransaction();
			restaurantDAO.delete(restaurant);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
			HibernateUtil.rollbackTransaction();
		}

	}

	@Override
	public Restaurant getRestaurantForFood(Food food) {
		Restaurant restaurant = null;
		try {
			restaurant = restaurantDAO.getRestaurantForFood(food);
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
		}
		return restaurant;
	}
}
