package com.homeacc.dto;

import java.math.BigDecimal;

public class MonthSumDTO {

	private Long monthNum;
	private String categoryName;
	private BigDecimal sum;

	public Long getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(Long monthNum) {
		this.monthNum = monthNum;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MonthSumDTO [monthNum=");
		builder.append(monthNum);
		builder.append(", categoryName=");
		builder.append(categoryName);
		builder.append(", sum=");
		builder.append(sum);
		builder.append("]");
		return builder.toString();
	}

}
