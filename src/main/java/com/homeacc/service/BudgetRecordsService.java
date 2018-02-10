package com.homeacc.service;

import java.time.LocalDate;

import com.homeacc.dto.IncomeDTO;

import javafx.collections.ObservableList;

public interface BudgetRecordsService {

	void saveOrUpdate(Long id, String userName, String categoryName, LocalDate created, String amount);

	void deleteBudgetRecords(Long id);

	ObservableList<IncomeDTO> getAll();
}
