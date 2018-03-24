package com.homeacc.repository.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.entity.BudgetRecord;
import com.homeacc.repository.BudgetRecordsRepository;

@Component
@Transactional
public class BudgetRecordsRepositoryImpl implements BudgetRecordsRepository {

	private final static String DELETE_CATEGORY = "delete from BudgetRecord where category_id = :categoryId";
	private final static String DELETE_USER = "delete from BudgetRecord where user_id = :userId";

	@Autowired
	private SessionFactory  sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetRecord> getAll(long groupId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BudgetRecord.class);
		criteria.add(Restrictions.eq("groups.id", groupId));
		return criteria.list();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetRecord> filterBudgetRecords(BudgetRecordsCriteriaFilter filter) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BudgetRecord.class);
		criteria.add(Restrictions.eq("groups.id", filter.getGroup()));
		if (filter.getUser() != null) {
			criteria.add(Restrictions.eq("users.id", filter.getUser().getId()));
		}
		if (filter.getCategory() != null) {
			criteria.add(Restrictions.eq("category.id", filter.getCategory().getId()));
		}
		if (filter.getBudgetType() != null) {
			criteria.add(Restrictions.eq("budgetType.id", filter.getBudgetType().getId()));
		}
		if (filter.getDateFrom() != null) {
			criteria.add(Restrictions.ge("created", filter.getDateFrom()));
		}
		if (filter.getDateTo() != null) {
			criteria.add(Restrictions.le("created", filter.getDateTo()));
		}
		if (filter.getAmountFrom() != null) {
			criteria.add(Restrictions.ge("amount", filter.getAmountFrom()));
		}
		if (filter.getAmountTo() != null) {
			criteria.add(Restrictions.le("amount", filter.getAmountTo()));
		}
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetRecord> getBudgetRecordsByDateAndBudgetType(long groupId, long budgetTypeId, Date from, Date to) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BudgetRecord.class);
		criteria.add(Restrictions.eq("groups.id", groupId));
		criteria.add(Restrictions.eq("budgetType.id", budgetTypeId));
		if (from != null) {
			criteria.add(Restrictions.ge("created", from));
		}
		if (to != null) {
			criteria.add(Restrictions.le("created", to));
		}
		return criteria.list();
	}
}
