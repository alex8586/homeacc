package com.homeacc.classifier;

public enum BudgetTypeEnum {

	INCOME(1, "INCOME"),
	EXPENSES(2, "EXPENSES"),
	ALL(100, "ALL BUDGET TYPES");

	private long id;
	private String code;

	BudgetTypeEnum(long id, String code) {
		this.id = id;
		this.code = code;
	}

	public long getId() {
		return id;
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
