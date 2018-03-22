package com.homeacc.service;

import java.util.List;

import com.homeacc.entity.Users;

public interface UserService {

	void createUser(String categoryName, long groupId);

	List<Users> getAll();

	Users getByName(String userName);

	Users updateUser(Users user);

	void deleteUser(Users user);
}
