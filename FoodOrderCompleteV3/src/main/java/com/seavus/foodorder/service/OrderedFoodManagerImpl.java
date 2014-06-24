package com.seavus.foodorder.service;

import org.hibernate.HibernateException;

import com.seavus.foodorder.dao.OrderedFoodDAO;
import com.seavus.foodorder.dao.OrderedFoodDAOImpl;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.OrderedFood;

public class OrderedFoodManagerImpl implements OrderedFoodManager {

	private OrderedFoodDAO orderedFoodDAO = new OrderedFoodDAOImpl();
	
	@Override
	public void saveNewOrderedFood(OrderedFood orderedFood) {

		try {
            HibernateUtil.beginTransaction();
            orderedFoodDAO.save(orderedFood);
            HibernateUtil.commitTransaction();
        } catch (HibernateException ex) {
            System.out.println("Handle error here");
            HibernateUtil.rollbackTransaction();
        }
	}

}
