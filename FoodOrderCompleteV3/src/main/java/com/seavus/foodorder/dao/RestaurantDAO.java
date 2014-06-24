package com.seavus.foodorder.dao;

import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Restaurant;

public interface RestaurantDAO extends GenericDAO<Restaurant, Long>{

	public Restaurant getRestaurantObjectForName(String name, String locale);
	
	public Restaurant getRestaurantForFood(Food food);
}
