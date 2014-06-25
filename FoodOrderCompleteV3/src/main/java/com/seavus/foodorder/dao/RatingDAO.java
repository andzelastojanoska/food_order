package com.seavus.foodorder.dao;

import com.seavus.foodorder.model.Rating;
import com.seavus.foodorder.model.Restaurant;

public interface RatingDAO extends GenericDAO<Rating, Long> {

	public double getRestaurantRating(Restaurant restaurant);
	
}
