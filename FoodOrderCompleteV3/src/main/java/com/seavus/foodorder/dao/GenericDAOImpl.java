package com.seavus.foodorder.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.seavus.foodorder.hibernateutil.HibernateUtil;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {
	
	protected Session getSession() {
		return HibernateUtil.getSession();
	}

	@Override
	public void save(T entity) {
		Session hibernateSession = this.getSession();
        hibernateSession.saveOrUpdate(entity);		
	}

	@Override
	public void merge(T entity) {
		Session hibernateSession = this.getSession();
        hibernateSession.merge(entity);		
	}

	@Override
	public void delete(T entity) {
		Session hibernateSession = this.getSession();
        hibernateSession.delete(entity);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findMany(org.hibernate.Query query) {
		List<T> t;
        t = (List<T>) query.list();
        return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findOne(org.hibernate.Query query) {
		T t;
        t = (T)  query.uniqueResult();
        return t;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> findAll(Class clazz) {
		Session hibernateSession = this.getSession();		
        List T = null;
        HibernateUtil.beginTransaction();
        org.hibernate.Query query = hibernateSession.createQuery("from " + clazz.getName());
        T = query.list();
        HibernateUtil.commitTransaction();
        return T;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T findByID(Class clazz, Long id) {
		Session hibernateSession = this.getSession();
        T t = null;
        t = (T) hibernateSession.get(clazz, id);
        return t;
	}

}
