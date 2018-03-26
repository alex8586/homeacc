package com.homeacc.repository;

import java.util.Date;
import java.util.List;

import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.entity.BudgetRecord;

public interface BudgetRecordsRepository {

	List<BudgetRecord> getAll(long groupId, int month);

	void deleteWithCategory(long categoryId);

	void deleteWithUser(long userId);

	List<BudgetRecord> filterBudgetRecords(BudgetRecordsCriteriaFilter criteria);

	List<BudgetRecord> getBudgetRecordsByDateAndBudgetType(long groupId, long budgetTypeId, Date from, Date to);
}
