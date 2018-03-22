package com.homeacc.repository;

import java.util.Date;
import java.util.List;

import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.entity.BudgetRecord;
import com.homeacc.entity.BudgetType;

public interface BudgetRecordsRepository {

	void save(BudgetRecord recor);

	void update(BudgetRecord record);

	void delete(BudgetRecord record);

	List<BudgetRecord> getAll(long groupId);

	BudgetRecord getById(long id);

	void deleteWithCategory(long categoryId);

	void deleteWithUser(long userId);

	List<BudgetType> getAllBudgetType();

	List<BudgetRecord> filterBudgetRecords(BudgetRecordsCriteriaFilter criteria);

	List<BudgetRecord> getBudgetRecordsByDateAndBudgetType(long groupId, long budgetTypeId, Date from, Date to);
}
