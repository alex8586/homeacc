package com.homeacc.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.Category;
import com.homeacc.repository.CategoryRepository;

@Component
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository{

	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public void save(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.save(category);
	}
}