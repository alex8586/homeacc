package com.homeacc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Users;
import com.homeacc.exception.EntityExistException;
import com.homeacc.repository.BudgetRecordsRepository;
import com.homeacc.repository.UserRepository;
import com.homeacc.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BudgetRecordsRepository incomeRepository;

	@Override
	public void createUser(String userName) {
		Users user = userRepository.getByName(userName);
		if (user != null) {
			throw new EntityExistException("User with this name already exist !");
		}
		user = new Users();
		user.setName(userName);
		userRepository.save(user);
	}

	@Override
	public Users updateUser(Users user) {
		userRepository.update(user);
		return userRepository.getByName(user.getName());
	}

	@Override
	public void deleteUser(Users user) {
		incomeRepository.deleteWithUser(user.getId());
		userRepository.delete(user);
	}

	@Override
	public Users getByName(String userName) {
		return userRepository.getByName(userName);
	}

	@Override
	public ObservableList<Users> getAll() {
		ObservableList<Users> users = FXCollections.observableArrayList();
		users.addAll(userRepository.getAll());
		return users;
	}

}
