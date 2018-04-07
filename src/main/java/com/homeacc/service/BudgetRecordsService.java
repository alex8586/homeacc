package com.homeacc.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.entity.BudgetType;

import javafx.collections.ObservableList;

public interface BudgetRecordsService {

	void saveOrUpdate(Long id, long groupId, String userName, String categoryName, String description,
			LocalDate created, BudgetType budgetType, String amount);

	void deleteBudgetRecord(Long id);

	ObservableList<BudgetRecordDTO> getAll(long groupId, Integer month);

	List<BudgetType> getAllBudgetType();

	List<BudgetRecordDTO> filterBudgetRecords(BudgetRecordsCriteriaFilter criteria);

	List<BudgetRecordDTO> getBudgetRecordsByDateAndBudgetType(long groupId, long budgetTypeId, Date from, Date to,
			int monthNumber);

}
