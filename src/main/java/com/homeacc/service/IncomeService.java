package com.homeacc.service;

import java.time.LocalDate;

public interface IncomeService {

	void save(String userName, String categoryName, LocalDate created, String amount);
}
