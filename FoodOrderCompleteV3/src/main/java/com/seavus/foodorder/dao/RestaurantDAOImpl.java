package com.seavus.foodorder.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Restaurant;

public class RestaurantDAOImpl extends GenericDAOImpl<Restaurant, Long>
		implements RestaurantDAO {

	@Override
	public Restaurant getRestaurantObjectForName(String name, String locale) {
		Restaurant restaurant = null;		
		String sql = "Select * from restaurants as r, localisedrestaurantnames_strings as names where names.strings = :name and names.strings_key = :locale and names.localisedrestaurantnames_id = r.name_id";
		HibernateUtil.beginTransaction();
//		String hql = "FROM Restaurant r WHERE r.nameStrings.strings[:locale] = :name";
        Query query = HibernateUtil.getSession().createSQLQuery(sql).addEntity(Restaurant.class);
        query.setString("name", name);
        query.setString("locale", locale);
        
        restaurant = (Restaurant) query.uniqueResult();
		HibernateUtil.commitTransaction();
		return restaurant;
	}
	
	@Override
	 public Restaurant getRestaurantForFood(Food food) {
		List<Restaurant> allRestaurants = findAll(Restaurant.class);
		  for (Restaurant restaurant : allRestaurants) {
			  Set<Food> foodsForRestaurant = restaurant.getFoods();
		   for (Food foodInRestaurant : foodsForRestaurant) {
		    if (foodInRestaurant.getName().equals(food.getName()) && foodInRestaurant.getRestaurant().getName().equals(food.getRestaurant().getName())) {
		     return restaurant;
		    }
		   }
		  }
		  return null;
	 }
}
