package com.homeacc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.dto.IncomeDTO;
import com.homeacc.entity.Category;
import com.homeacc.entity.Income;
import com.homeacc.entity.Users;
import com.homeacc.mapper.Mapper;
import com.homeacc.repository.CategoryRepository;
import com.homeacc.repository.IncomeRepository;
import com.homeacc.repository.UserRepository;
import com.homeacc.service.IncomeService;
import com.homeacc.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional
	public void saveOrUpdate(Long id,String userName, String categoryName, LocalDate date, String amount) {
		Income income = getIncome(id, userName, categoryName, date, amount);
		if (id == null) {
			incomeRepository.save(income);
		} else {
			incomeRepository.update(income);
		}
	}

	private Income getIncome(Long id,String userName, String categoryName, LocalDate date, String amount) {
		Users user = userRepository.getByName(userName);
		Category category = categoryRepository.getByName(categoryName);

		Income income = id == null ? new Income() : incomeRepository.getById(id);
		income.setUsers(user);
		income.setCategory(category);
		income.setCreated(DateUtils.localDateToDate(date));
		income.setAmount(new BigDecimal(amount));
		return income;
	}

	@Override
	@Transactional
	public void deleteIncome(Long id) {
		Income income = incomeRepository.getById(id);
		incomeRepository.delete(income);
	}

	@Override
	@Transactional
	public ObservableList<IncomeDTO> getAll() {
		ObservableList<IncomeDTO> incomes = FXCollections.observableArrayList();
		List<Income> list = incomeRepository.getAll();
		incomes.addAll(Mapper.mapIncomeListToDtoList(list));
		return incomes;
	}

}
