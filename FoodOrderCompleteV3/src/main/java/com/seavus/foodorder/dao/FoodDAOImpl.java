package com.seavus.foodorder.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Food;
import com.seavus.foodorder.model.Order;
import com.seavus.foodorder.model.OrderedFood;
import com.seavus.foodorder.model.Restaurant;

public class FoodDAOImpl extends GenericDAOImpl<Food, Long> implements FoodDAO {

	@Override
	public List<Food> getFoodForRestaurant(Restaurant restaurant) {
		ArrayList<Food> foods = null;
		String sql = "FROM Food F WHERE F.restaurant.id = :restaurant_id";
		try {

			Query query = HibernateUtil.getSession().createQuery(sql);
			query.setParameter("restaurant_id", restaurant.getId());
			foods = (ArrayList<Food>)query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if (foods.size() == 0) {
			return null;
		} else {
			return foods;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRestaurantFoodTypes(Restaurant restaurant, String language) {
		List<String> results = null;
//		String sql = "SELECT LS.strings FROM localised_strings AS LS, foods AS F WHERE LS.strings_key = :locale AND LS.localised_id = F.type_id";
		String sql = "Select localised_types.strings From localisedfoodtypes_strings As localised_types, foods As f Where f.restaurant_id = :id And f.type_id = localised_types.localisedfoodtypes_id And localised_types.strings_key = :language";
		
//		String hql = "SELECT F.type FROM Food F WHERE F.restaurant.id = :id";

		try {
			HibernateUtil.beginTransaction();
//			Query query = HibernateUtil.getSession().createQuery(hql)
//					.setParameter("id", restaurant.getId());
			Query query = HibernateUtil.getSession().createSQLQuery(sql);
			query.setParameter("id", restaurant.getId());
			query.setParameter("language", language);
			results = query.list();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		if (results.size() == 0) {
			return null;
		} else {
			return results;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Food> getFoodForOrder(Order order) {
		String hql = "FROM OrderedFood O WHERE O.order.id = :id";
		Query query = HibernateUtil.getSession().createQuery(hql)
				.setParameter("id", order.getId());
		List<OrderedFood> orderedFoods = query.list();
		List<Food> foods = new ArrayList<>();
		for (OrderedFood of : orderedFoods) {
			hql = "FROM Food F WHERE F.id = :id";
			query = HibernateUtil.getSession().createQuery(hql)
					.setParameter("id", of.getFood().getId());
			Food food = (Food) query.list().get(0);
			foods.add(food);
		}
		if (foods.size() == 0) {
			return null;
		} else {
			return foods;
		}
	}

	@Override
	public Food getFoodForRestaurantByName(String name, Restaurant restaurant) {
		List<Food> foods = getFoodForRestaurant(restaurant);
		for (Food food : foods) {
			if (name.equals(food.getName())) {
				return food;
			}
		}
		return null;
	}

}
