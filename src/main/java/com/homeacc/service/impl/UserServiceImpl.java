package com.homeacc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Groups;
import com.homeacc.entity.Users;
import com.homeacc.exception.ValidationException;
import com.homeacc.repository.BudgetRecordsRepository;
import com.homeacc.repository.GenericRepository;
import com.homeacc.repository.UserRepository;
import com.homeacc.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BudgetRecordsRepository incomeRepository;
	@Autowired
	private GenericRepository genericRepository;

	@Override
	public void createUser(String userName, long groupId) {
		Users user = userRepository.getByName(userName);
		if (user != null) {
			throw new ValidationException("User with this name already exist !");
		}
		user = new Users();
		user.setName(userName);
		Groups group = genericRepository.getById(Groups.class, groupId);
		user.setGroups(group);
		genericRepository.save(user);
	}

	@Override
	public Users updateUser(Users user) {
		genericRepository.update(user);
		return userRepository.getByName(user.getName());
	}

	@Override
	public void deleteUser(Users user) {
		incomeRepository.deleteWithUser(user.getId());
		genericRepository.delete(user);
	}

	@Override
	public Users getByName(String userName) {
		return userRepository.getByName(userName);
	}

	@Override
	public List<Users> getAll(long groupId) {
		return userRepository.getByGroup(groupId);
	}

}
