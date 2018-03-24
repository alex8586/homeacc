package com.homeacc.repository;

import java.util.List;

import com.homeacc.entity.Users;

public interface UserRepository {

	List<Users> getByGroup(long groupId);

	Users getByName(String name);

}
