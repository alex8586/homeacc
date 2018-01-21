package com.homeacc.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.entity.Income;
import com.homeacc.entity.Users;
import com.homeacc.repository.CategoryRepository;
import com.homeacc.repository.IncomeRepository;
import com.homeacc.repository.UserRepository;
import com.homeacc.service.IncomeService;

@Component
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void save(String userName, String categoryName, LocalDate date, String amount) {
		Users user = userRepository.getByName(userName);
		Category category = categoryRepository.getByName(categoryName);

		Date created = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Income income = new Income();
		income.setUsers(user);
		income.setCategory(category);
		income.setCreated(created);
		income.setAmount(Long.parseLong(amount));
		incomeRepository.save(income);
	}

}
