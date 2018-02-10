package com.homeacc.repository;

import java.util.List;

import com.homeacc.entity.BudgetRecord;

public interface BudgetRecordsRepository {

	void save(BudgetRecord income);

	void update(BudgetRecord income);

	void delete(BudgetRecord income);

	List<BudgetRecord> getAll();

	BudgetRecord getById(long id);

	void deleteWithCategory(long categoryId);

	void deleteWithUser(long userId);
}
