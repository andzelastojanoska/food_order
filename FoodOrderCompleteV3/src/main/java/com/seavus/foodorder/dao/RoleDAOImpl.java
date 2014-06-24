package com.seavus.foodorder.dao;

import org.hibernate.Query;

import com.seavus.foodorder.hibernateutil.HibernateUtil;
import com.seavus.foodorder.model.Role;

public class RoleDAOImpl extends GenericDAOImpl<Role, Long> implements RoleDAO {

	@Override
	public Role getRole(String modifier) {
		Role role = null;
		String hql = "FROM Role r WHERE r.role = :role";
		Query query = (Query) HibernateUtil.getSession().createQuery(hql);
		query.setString("role", modifier);
		role = findOne(query);
		return role;
	}
}
