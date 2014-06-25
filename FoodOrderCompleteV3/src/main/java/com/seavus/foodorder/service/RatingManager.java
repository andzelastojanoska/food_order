package com.seavus.foodorder.service;

import com.seavus.foodorder.model.Rating;
import com.seavus.foodorder.model.Restaurant;

public interface RatingManager {

	public void saveRating(Rating rating);
	
	public double getRestaurantRating(Restaurant restaurant);
}
