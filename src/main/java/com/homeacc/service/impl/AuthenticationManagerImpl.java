package com.homeacc.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Groups;
import com.homeacc.exception.ValidationException;
import com.homeacc.repository.GenericRepository;
import com.homeacc.repository.GroupRepository;
import com.homeacc.service.AuthenticationManager;

@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GenericRepository genericRepository;

	@Override
	public void registerGroup(String name, String password) {
		validateFields(name, password);
		if (name.length() > 20 || password.length() > 20) {
			throw new ValidationException("Field length exceeded");
		}
		Groups group = groupRepository.getByNameAndPassword(name, password);
		if (group != null) {
			throw new ValidationException("Group with this name already exist");
		}

		group = new Groups();
		group.setName(name);
		group.setPassword(password);
		genericRepository.save(group);
	}

	@Override
	public void loginGroup(String name, String password) {
		validateFields(name, password);
		Groups group = groupRepository.getByNameAndPassword(name, password);
		if (group == null) {
			throw new ValidationException("Group with name " + name + " not found");
		}
	}

	private void validateFields(String name, String password) {
		if (StringUtils.isAnyBlank(name, password)) {
			throw new ValidationException("All fields must be filled");
		}
	}
}
