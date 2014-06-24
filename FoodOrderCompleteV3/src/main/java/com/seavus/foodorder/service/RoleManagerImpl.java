package com.seavus.foodorder.service;

import org.hibernate.HibernateException;

import com.seavus.foodorder.dao.RoleDAO;
import com.seavus.foodorder.dao.RoleDAOImpl;
import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Role;

public class RoleManagerImpl implements RoleManager {

	private RoleDAO roleDAO = new RoleDAOImpl();

	@Override
	public void createNewRole(Role role) {
		try {
			HibernateUtil.beginTransaction();
			roleDAO.save(role);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
			HibernateUtil.rollbackTransaction();
		}

	}

	@Override
	public Role getRole(String modifier) {
		Role role = null;
		try {
			HibernateUtil.beginTransaction();
			role = roleDAO.getRole(modifier);
			HibernateUtil.commitTransaction();
		} catch (HibernateException ex) {
			System.out.println("Handle error here");
			HibernateUtil.rollbackTransaction();
		}
		return role;
	}
}
