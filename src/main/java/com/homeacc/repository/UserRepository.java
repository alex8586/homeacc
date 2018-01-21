package com.homeacc.repository;

import java.util.List;

import com.homeacc.entity.Users;

public interface UserRepository {

	void save(Users user);

	List<Users> getAll();

	Users getByName(String name);
}
