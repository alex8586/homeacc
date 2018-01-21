package com.homeacc.service;

import com.homeacc.entity.Users;

import javafx.collections.ObservableList;

public interface UserService {

	void save(String userName);

	ObservableList<Users> getAll();
}
