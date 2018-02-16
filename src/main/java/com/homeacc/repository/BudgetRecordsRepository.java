package com.homeacc.repository;

import java.util.List;

import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.entity.BudgetRecord;
import com.homeacc.entity.BudgetType;

public interface BudgetRecordsRepository {

	void save(BudgetRecord income);

	void update(BudgetRecord income);

	void delete(BudgetRecord income);

	List<BudgetRecord> getAll();

	BudgetRecord getById(long id);

	void deleteWithCategory(long categoryId);

	void deleteWithUser(long userId);

	List<BudgetType> getAllBudgetType();

	List<BudgetRecord> filterBudgetRecords(BudgetRecordsCriteriaFilter criteria);
}
