package com.homeacc.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

	@Override
	public void update(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.update(category);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll(long groupId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Category.class);
		criteria.add(Restrictions.eq("groups.id", groupId));
		return criteria.list();
	}

	@Override
	public void delete(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(category);
	}

	@Override
	public Category getByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Category.class);
		criteria.add(Restrictions.eq("name", name));
		return (Category) criteria.uniqueResult();
	}
}