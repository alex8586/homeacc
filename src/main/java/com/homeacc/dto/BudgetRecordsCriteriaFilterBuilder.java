package com.homeacc.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.homeacc.entity.BudgetType;
import com.homeacc.entity.Category;
import com.homeacc.entity.Users;

public class BudgetRecordsCriteriaFilterBuilder {

	private long groupId;
	private Users user;
	private Category category;
	private BudgetType budgetType;
	private Date dateFrom;
	private Date dateTo;
	private BigDecimal amountFrom;
	private BigDecimal amountTo;

	public BudgetRecordsCriteriaFilterBuilder() {

	}

	public static BudgetRecordsCriteriaFilterBuilder createBudgetRecordsCriteraFilter() {
        return new BudgetRecordsCriteriaFilterBuilder();
	}

	public BudgetRecordsCriteriaFilter build() {
		BudgetRecordsCriteriaFilter criteria = new BudgetRecordsCriteriaFilter();
		criteria.setGroupId(groupId);
		criteria.setUser(user);
		criteria.setCategory(category);
		criteria.setBudgetType(budgetType);
		criteria.setDateFrom(dateFrom);
		criteria.setDateTo(dateTo);
		criteria.setAmountFrom(amountFrom);
		criteria.setAmountTo(amountTo);
		return criteria;
	}

	public BudgetRecordsCriteriaFilterBuilder withGroupId(long groupId) {
        this.groupId = groupId;
        return this;
	}
	public BudgetRecordsCriteriaFilterBuilder withUser(Users user) {
        this.user = user;
        return this;
	}

	public BudgetRecordsCriteriaFilterBuilder withCategory(Category category) {
        this.category = category;
        return this;
	}

	public BudgetRecordsCriteriaFilterBuilder withBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
        return this;
	}

	public BudgetRecordsCriteriaFilterBuilder withDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
        return this;
	}

	public BudgetRecordsCriteriaFilterBuilder withDateTo(Date dateTo) {
        this.dateTo = dateTo;
        return this;
	}

	public BudgetRecordsCriteriaFilterBuilder withAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
        return this;
	}

	public BudgetRecordsCriteriaFilterBuilder withAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
        return this;
	}

}
