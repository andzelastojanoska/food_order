package com.seavus.foodorder.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Rating;
import com.seavus.foodorder.model.Restaurant;

public class RatingDAOImpl extends GenericDAOImpl<Rating, Long> implements
		RatingDAO {

	@Override
	public double getRestaurantRating(Restaurant restaurant) {
		
		List<Rating> ratings = null;
		String sql = "FROM Rating R WHERE R.restaurant.id = :restaurant_id";
		try {

			Query query = HibernateUtil.getSession().createQuery(sql);
			query.setParameter("restaurant_id", restaurant.getId());
			ratings = (List<Rating>)query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if (ratings.size() == 0) {
			return 0;
		} else {
			double average = 0;
			for (Rating rating : ratings) {
				average += rating.getRating();
			}
			return average / ratings.size();
		}
	}

}
