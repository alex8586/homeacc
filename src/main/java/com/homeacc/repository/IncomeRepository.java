package com.homeacc.repository;

import java.util.List;

import com.homeacc.entity.Income;

public interface IncomeRepository {

	void save(Income income);

	void update(Income income);

	List<Income> getAll();

	Income getById(long id);

}
