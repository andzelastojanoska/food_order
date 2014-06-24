package com.seavus.foodorder.dao;

import java.util.List;

import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.Restaurant;

public interface FoodDAO extends GenericDAO<Food, Long> {

	public List<Food> getFoodForRestaurant(Restaurant restaurant);

	public List<String> getRestaurantFoodTypes(Restaurant restaurant, String language);

	public List<Food> getFoodForOrder(Order order);
	
	public Food getFoodForRestaurantByName(String name, Restaurant restaurant);

}
