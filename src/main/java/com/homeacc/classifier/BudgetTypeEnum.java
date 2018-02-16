package com.homeacc.classifier;

public enum BudgetTypeEnum {

	INCOME("INCOME"),
	EXPENSES("EXPENSES"),
	ALL("ALL BUDGET TYPES");

	private String code;

	BudgetTypeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public BudgetTypeEnum getByCode(String code) {
		for(BudgetTypeEnum type : BudgetTypeEnum.values()) {
			if (type.getCode().equals(code)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Type not found");
	}

}
