package com.homeacc.service;

import java.time.LocalDate;

import com.homeacc.dto.IncomeDTO;

import javafx.collections.ObservableList;

public interface IncomeService {

	void save(String userName, String categoryName, LocalDate created, String amount);

	ObservableList<IncomeDTO> getAll();
}
