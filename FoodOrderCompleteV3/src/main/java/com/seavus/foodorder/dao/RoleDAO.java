package com.seavus.foodorder.dao;

import com.seavus.foodorder.model.Role;

public interface RoleDAO extends GenericDAO<Role, Long> {

	public Role getRole(String modifier);
	
}
