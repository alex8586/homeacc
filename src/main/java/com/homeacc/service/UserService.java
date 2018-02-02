package com.homeacc.service;

import com.homeacc.entity.Users;

import javafx.collections.ObservableList;

public interface UserService {

	void createUser(String categoryName);

	ObservableList<Users> getAll();

	Users getByName(String userName);

	Users updateUser(Users user);

	void deleteUser(Users user);
}
