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

	@Override
	public void save(Users user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Override
	public void update(Users user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public void delete(Users user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Users.class);
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
