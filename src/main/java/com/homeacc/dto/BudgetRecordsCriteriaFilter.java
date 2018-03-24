package com.homeacc.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.homeacc.entity.BudgetType;
import com.homeacc.entity.Category;
import com.homeacc.entity.Users;

public class BudgetRecordsCriteriaFilter {

	private long groupId;
	private Users user;
	private Category category;
	private BudgetType budgetType;
	private Date dateFrom;
	private Date dateTo;
	private BigDecimal amountFrom;
	private BigDecimal amountTo;

	public long getGroup() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public BudgetType getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public BigDecimal getAmountFrom() {
		return amountFrom;
	}
	public void setAmountFrom(BigDecimal amountFrom) {
		this.amountFrom = amountFrom;
	}
	public BigDecimal getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(BigDecimal amountTo) {
		this.amountTo = amountTo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BudgetRecordsCriteriaFilter [groupId=");
		builder.append(groupId);
		builder.append(", user=");
		builder.append(user);
		builder.append(", category=");
		builder.append(category);
		builder.append(", budgetType=");
		builder.append(budgetType);
		builder.append(", dateFrom=");
		builder.append(dateFrom);
		builder.append(", dateTo=");
		builder.append(dateTo);
		builder.append(", amountFrom=");
		builder.append(amountFrom);
		builder.append(", amountTo=");
		builder.append(amountTo);
		builder.append("]");
		return builder.toString();
	}

}
