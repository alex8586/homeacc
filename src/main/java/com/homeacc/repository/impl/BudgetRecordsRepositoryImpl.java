package com.homeacc.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.BudgetRecord;
import com.homeacc.repository.BudgetRecordsRepository;

@Component
@Transactional
public class BudgetRecordsRepositoryImpl implements BudgetRecordsRepository {

	private final static String DELETE_CATEGORY = "delete from BudgetRecord where category_id = :categoryId";
	private final static String DELETE_USER = "delete from BudgetRecord where user_id = :userId";

	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public void save(BudgetRecord record) {
		Session session = sessionFactory.getCurrentSession();
		session.save(record);
	}

	@Override
	public void update(BudgetRecord record) {
		Session session = sessionFactory.getCurrentSession();
		session.update(record);
	}

	@Override
	public void delete(BudgetRecord record) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetRecord> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BudgetRecord.class);
		return criteria.list();
	}

	@Override
	public BudgetRecord getById(long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(BudgetRecord.class);
		criteria.add(Restrictions.eq("id", id));
		return (BudgetRecord) criteria.uniqueResult();
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
