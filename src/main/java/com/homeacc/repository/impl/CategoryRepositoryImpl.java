package com.homeacc.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll(long groupId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
		criteria.add(Restrictions.eq("groups.id", groupId));
		return criteria.list();
	}

	@Override
	public Category getByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
		criteria.add(Restrictions.eq("name", name));
		return (Category) criteria.uniqueResult();
	}
}