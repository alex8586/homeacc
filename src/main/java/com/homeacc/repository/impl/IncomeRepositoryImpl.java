package com.homeacc.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.Income;
import com.homeacc.repository.IncomeRepository;

@Component
@Transactional
public class IncomeRepositoryImpl implements IncomeRepository {

	private final static String DELETE_CATEGORY = "delete from Income where category_id = :categoryId";
	private final static String DELETE_USER = "delete from Income where user_id = :userId";

	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public void save(Income income) {
		Session session = sessionFactory.getCurrentSession();
		session.save(income);
	}

	@Override
	public void update(Income income) {
		Session session = sessionFactory.getCurrentSession();
		session.update(income);
	}

	@Override
	public void delete(Income income) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(income);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Income> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Income.class);
		return criteria.list();
	}

	@Override
	public Income getById(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Income.class);
		criteria.add(Restrictions.eq("id", id));
		return (Income) criteria.uniqueResult();
	}

	@Override
	public void deleteWithCategory(long categoryId) {
		Session session = sessionFactory.getCurrentSession();
		session.createQuery(DELETE_CATEGORY)
		.setLong("categoryId", categoryId)
		.executeUpdate();
	}

	@Override
	public void deleteWithUser(long userId) {
		Session session = sessionFactory.getCurrentSession();
		session.createQuery(DELETE_USER)
		.setLong("userId", userId)
		.executeUpdate();
	}
}
