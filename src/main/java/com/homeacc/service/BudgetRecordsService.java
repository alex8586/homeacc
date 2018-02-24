package com.homeacc.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.entity.BudgetType;

import javafx.collections.ObservableList;

public interface BudgetRecordsService {

	void saveOrUpdate(Long id, String userName, String categoryName, LocalDate created, BudgetType budgetType, String amount);

	void deleteBudgetRecords(Long id);

	ObservableList<BudgetRecordDTO> getAll();

	List<BudgetType> getAllBudgetType();

	List<BudgetRecordDTO> filterBudgetRecords(BudgetRecordsCriteriaFilter criteria);

	List<BudgetRecordDTO> getBudgetRecordsByDateAndBudgetType(long budgetTypeId, Date from, Date to);
}
