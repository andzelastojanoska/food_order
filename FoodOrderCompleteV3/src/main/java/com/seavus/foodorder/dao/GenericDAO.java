package com.seavus.foodorder.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

	public void save(T entity);
	 
    public void merge(T entity);
 
    public void delete(T entity);
 
    public List<T> findMany(org.hibernate.Query query);
 
    public T findOne(org.hibernate.Query query);
 
    @SuppressWarnings("rawtypes")
	public List findAll(Class clazz);
 
    @SuppressWarnings("rawtypes")
	public T findByID(Class clazz, Long id);
}
