package com.homeacc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Users;
import com.homeacc.repository.UserRepository;
import com.homeacc.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(String userName) {
		Users user = new Users();
		user.setName(userName);
		userRepository.save(user);
	}

}
