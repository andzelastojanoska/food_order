package com.seavus.foodorder.service;

import java.util.List;

import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Restaurant;

public interface RestaurantManager {

	public List<Restaurant> loadAllRestaurants();
	
	public Restaurant getRestaurantObjectForName(String name, String locale);
	
	public void addRestaurant(Restaurant restaurant);
	
	public void updateRestaurant(String name, String phoneNumber, Restaurant restaurant);
	
	public void updateRestaurant(String name, String phoneNumber, String language, String localisedName, Restaurant restaurant);
	
	public void deleteRestaurant(Restaurant restaurant);
	
	public Restaurant getRestaurantForFood(Food food);
}
