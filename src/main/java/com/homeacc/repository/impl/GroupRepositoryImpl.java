package com.homeacc.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.Groups;
import com.homeacc.repository.GroupRepository;

@Component
@Transactional
public class GroupRepositoryImpl implements GroupRepository {

	@Autowired
	private SessionFactory  sessionFactory;

	@Override
	public Groups getByNameAndPassword(String name, String password) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Groups.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("password", password));
		return (Groups) criteria.uniqueResult();
	}
}
