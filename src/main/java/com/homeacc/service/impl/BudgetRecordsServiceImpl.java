package com.homeacc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.entity.BudgetRecord;
import com.homeacc.entity.BudgetType;
import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.mapper.Mapper;
import com.homeacc.repository.BudgetRecordsRepository;
import com.homeacc.repository.CategoryRepository;
import com.homeacc.repository.GenericRepository;
import com.homeacc.repository.UserRepository;
import com.homeacc.service.BudgetRecordsService;
import com.homeacc.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class BudgetRecordsServiceImpl implements BudgetRecordsService {

	@Autowired
	private BudgetRecordsRepository budgetRecordsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private GenericRepository genericRepository;

	@Override
	@Transactional
	public void saveOrUpdate(Long id, String userName, String categoryName, LocalDate date, BudgetType budgetType,
			String amount) {
		BudgetRecord record = getRecord(id, userName, categoryName, date, budgetType, amount);
		if (id == null) {
			genericRepository.save(record);
		} else {
			genericRepository.update(record);
		}
	}

	private BudgetRecord getRecord(Long id, String userName, String categoryName, LocalDate date, BudgetType budgetType,
			String amount) {
		Users user = userRepository.getByName(userName);
		Category category = categoryRepository.getByName(categoryName);

		BudgetRecord record = id == null ? new BudgetRecord() : genericRepository.getById(BudgetRecord.class, id);
		record.setUsers(user);
		record.setCategory(category);
		record.setCreated(DateUtils.localDateToDate(date));
		record.setBudgetType(budgetType);
		record.setAmount(new BigDecimal(amount));
		return record;
	}

	@Override
	@Transactional
	public void deleteBudgetRecords(Long id) {
		BudgetRecord record = budgetRecordsRepository.getById(id);
		budgetRecordsRepository.delete(record);
	}

	@Override
	@Transactional
	public ObservableList<BudgetRecordDTO> getAll() {
		ObservableList<BudgetRecordDTO> records = FXCollections.observableArrayList();
		List<BudgetRecord> list = budgetRecordsRepository.getAll();
		records.addAll(Mapper.mapBudgetRecordsListToDtoList(list));
		return records;
	}

	@Override
	public List<BudgetType> getAllBudgetType() {
		return genericRepository.getAll(BudgetType.class);
	}

	@Override
	@Transactional
	public List<BudgetRecordDTO> filterBudgetRecords(BudgetRecordsCriteriaFilter criteria) {
		List<BudgetRecord> records = budgetRecordsRepository.filterBudgetRecords(criteria);
		return Mapper.mapBudgetRecordsListToDtoList(records);
	}

	@Override
	@Transactional
	public List<BudgetRecordDTO> getBudgetRecordsByDateAndBudgetType(long budgetTypeId, Date from, Date to) {
		List<BudgetRecord> records = budgetRecordsRepository.getBudgetRecordsByDateAndBudgetType(budgetTypeId, from,
				to);
		return Mapper.mapBudgetRecordsListToDtoList(records);
	}
}
