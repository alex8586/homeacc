package com.homeacc.service;

import java.time.LocalDate;

import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.entity.BudgetType;

import javafx.collections.ObservableList;

public interface BudgetRecordsService {

	void saveOrUpdate(Long id, String userName, String categoryName, LocalDate created, BudgetType budgetType, String amount);

	void deleteBudgetRecords(Long id);

	ObservableList<BudgetRecordDTO> getAll();

	ObservableList<BudgetType> getAllBudgetType();
}
