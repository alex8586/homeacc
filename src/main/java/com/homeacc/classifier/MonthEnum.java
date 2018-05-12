package com.homeacc.classifier;

import java.util.ArrayList;
import java.util.List;

public enum MonthEnum {

	UNDEFINED(0),
	JANUARY(1),
	FEBRUARY(2),
	MARCH(3),
	APRIL(4),
	MAY(5),
	JUNE(6),
	JULY(7),
	AUGUST(8),
	SEPTEMBER(9),
	OCTOBER(10),
	NOVEMBER(11),
	DECEMBER(12);

	private int monthNumber;

	MonthEnum(int monthNumber) {
		this.monthNumber = monthNumber;
	}

	public int getMonthNumber() {
		return monthNumber;
	}

	private static MonthEnum getByNumber(int number) {
		for (MonthEnum month : MonthEnum.values()) {
			if (month.getMonthNumber() == number) {
				return month;
			}
		}
		throw new IllegalArgumentException("Month not found");
	}

	public static List<MonthEnum> getMonthsForOneYearInOrder(int number) {
		number = number + 2;
		List<MonthEnum> months = new ArrayList<>();
		int count = 0;
		while(count < 12) {
			if (number < 13) {
				months.add(getByNumber(number));
				count++;
				number++;
			} else {
				number = 1;
				months.add(getByNumber(number));
				count++;
				number++;
			}
		}

		return months;
	}
}
