package com.seavus.foodorder.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import com.seavus.foodorder.dao.FoodDAO;
import com.seavus.foodorder.dao.FoodDAOImpl;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Restaurant;

public class FoodManagerImpl implements FoodManager {

	private FoodDAO foodDAO = new FoodDAOImpl();

	@Override
	public List<Food> getFoodForRestaurant(Restaurant restaurant) {
		ArrayList<Food> foods = null;
		try {
			HibernateUtil.beginTransaction();
			foods = (ArrayList<Food>) foodDAO.getFoodForRestaurant(restaurant);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			// Error handling code
		}
		return foods;
	}

	@Override
	public List<String> getRestaurantFoodTypes(Restaurant restaurant, String language) {
		List<String> types = null;
		types = (List<String>) foodDAO.getRestaurantFoodTypes(restaurant, language);
		return types;
	}

	@Override
	public List<Food> getFoodForOrder(Order order) {
		ArrayList<Food> foods = null;
		try {
			HibernateUtil.beginTransaction();
			foods = (ArrayList<Food>) foodDAO.getFoodForOrder(order);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			// Error handling code
		}
		return foods;
	}

	@Override
	public void addFood(Food food) {
		try {
            HibernateUtil.beginTransaction();
            foodDAO.save(food);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle error here");
            HibernateUtil.rollbackTransaction();
        }
		
	}

	@Override
	public void deleteFood(Food food) {
		try {
            HibernateUtil.beginTransaction();
            foodDAO.delete(food);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle error here");
            HibernateUtil.rollbackTransaction();
        }
		
	}

	@Override
	public void updateFood(String name, String type, double price,
			Restaurant restaurant, Food food) {
		food.setName(name);
		food.setType(type);
		food.setPrice(price);
		food.setRestaurant(restaurant);
		try {
			HibernateUtil.beginTransaction();
			foodDAO.merge(food);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			// handle exception
		}
		
	}
	
	@Override
	public void updateFood(String name, String type, double price, String language, String localisedName, String localisedType, Restaurant restaurant, Food food) {
		
		food.setName(name);
		food.setType(type);
		food.setPrice(price);
		food.setRestaurant(restaurant);
		food.setName(language, localisedName);
		food.setType(language, localisedType);
		try {
			HibernateUtil.beginTransaction();
			foodDAO.merge(food);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			// handle exception
		}
	}

	@Override
	public Food getFoodForRestaurantByName(String name, Restaurant restaurant) {
		Food food = null;
		try {
            HibernateUtil.beginTransaction();
            food = foodDAO.getFoodForRestaurantByName(name, restaurant);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle error here");
            HibernateUtil.rollbackTransaction();
        }
		return food;
	}
}
