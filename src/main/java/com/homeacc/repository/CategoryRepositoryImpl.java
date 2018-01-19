package com.homeacc.repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.Category;

@Component
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository{

	@Autowired
	private SessionFactory  sessionFactory;

	public CategoryRepositoryImpl() {
		System.out.println("== in CategoryRepositoryImpl");
	}

	@Override
	public void save(Category category) {
		System.out.println("== in repository save " + category.getName());
		Session session = sessionFactory.getCurrentSession();
		session.save(category);
	}
}