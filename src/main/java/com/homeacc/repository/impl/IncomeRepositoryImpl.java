package com.homeacc.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.Income;
import com.homeacc.repository.IncomeRepository;

@Component
@Transactional
public class IncomeRepositoryImpl implements IncomeRepository {

	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public void save(Income income) {
		Session session = sessionFactory.getCurrentSession();
		session.save(income);
	}

}
