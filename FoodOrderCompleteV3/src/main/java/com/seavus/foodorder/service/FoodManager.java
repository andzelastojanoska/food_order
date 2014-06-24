package com.seavus.foodorder.service;

import java.util.List;

import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Restaurant;

public interface FoodManager {

	public List<Food> getFoodForRestaurant(Restaurant restaurant);
	
	public List<String> getRestaurantFoodTypes(Restaurant restaurant, String language);
	
	public List<Food> getFoodForOrder(Order order);
	
	public void addFood(Food food);
	
	public void deleteFood(Food food);
	
	public void updateFood(String name, String type, double price, Restaurant restaurant, Food food);
	
	public Food getFoodForRestaurantByName(String name, Restaurant restaurant);
}
