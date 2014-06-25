package com.seavus.foodorder.service;

import org.hibernate.HibernateException;

import com.seavus.foodorder.dao.RatingDAO;
import com.seavus.foodorder.dao.RatingDAOImpl;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Rating;
import com.seavus.foodorder.model.Restaurant;

public class RatingManagerImpl implements RatingManager {

	private RatingDAO ratingDAO = new RatingDAOImpl();
	
	@Override
	public void saveRating(Rating rating) {
		try {
            HibernateUtil.beginTransaction();
            ratingDAO.save(rating);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle error here");
            HibernateUtil.rollbackTransaction();
        }
	}

	@Override
	public double getRestaurantRating(Restaurant restaurant) {
		
		double rating = 0;
		
		try {
            HibernateUtil.beginTransaction();
            rating = ratingDAO.getRestaurantRating(restaurant);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle error here");
            HibernateUtil.rollbackTransaction();
        }
		return rating;
	}

}
