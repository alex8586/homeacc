package com.homeacc.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.Users;
import com.homeacc.repository.UserRepository;

@Component
@Transactional
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getByGroup(long groupId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Users.class);
		criteria.add(Restrictions.eq("groups.id", groupId));
		return criteria.list();
	}

	@Override
	public Users getByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Users.class);
		criteria.add(Restrictions.eq("name", name));
		return (Users) criteria.uniqueResult();
	}

}
