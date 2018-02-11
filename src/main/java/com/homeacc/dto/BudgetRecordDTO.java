package com.homeacc.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BudgetRecordDTO {

	private long id;
	private String userName;
	private String categoryName;
	private Date created;
	private String budgetType;
	private BigDecimal amount;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BudgetRecordDTO [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", categoryName=");
		builder.append(categoryName);
		builder.append(", created=");
		builder.append(created);
		builder.append(", budgetType=");
		builder.append(budgetType);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}

}
