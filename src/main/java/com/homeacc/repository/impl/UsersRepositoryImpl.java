package com.homeacc.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.entity.Users;
import com.homeacc.repository.UsersRepository;

@Component
@Transactional
public class UsersRepositoryImpl implements UsersRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(Users user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

}