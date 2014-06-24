package com.seavus.foodorder.service;

import com.seavus.foodorder.model.Role;

public interface RoleManager {

	public void createNewRole(Role role);
	
	public Role getRole(String modifier);
}
