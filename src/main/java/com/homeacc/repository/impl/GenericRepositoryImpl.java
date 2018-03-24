package com.homeacc.repository.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.repository.GenericRepository;

@Transactional
@Component
public class GenericRepositoryImpl implements GenericRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public <T> void save(T clazz) {
		Session session = sessionFactory.getCurrentSession();
		session.save(clazz);
	}

	@Override
	public <T> void update(T clazz) {
		Session session = sessionFactory.getCurrentSession();
		session.update(clazz);
	}

	@Override
	public <T> void delete(T clazz) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getById(Class<T> clazz, long id) {
		Session session = sessionFactory.getCurrentSession();
		return (T) session.get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAll(Class<T> clazz) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
		return criteria.list();
	}

}
