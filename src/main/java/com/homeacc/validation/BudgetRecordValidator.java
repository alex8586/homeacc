package com.homeacc.validation;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.exception.ValidationException;

public class BudgetRecordValidator {

	public static void validateFields(Users user, Category category, LocalDate date, String amount) {
		if (user == null || category == null || StringUtils.isBlank(amount) || date == null) {
			throw new ValidationException("all fields exept description must be filled");
		}
	}

	public static void validateDescription(String description) {
		if (description.length() > 30) {
			throw new ValidationException("Description length must be not more 30 symbols");
		}
	}

	public static void validateAmount(String amount) {
		if (!amount.matches("^[0-9]{1,15}([\\.][0-9]{1,2}){0,1}$")) {
			throw new ValidationException("amount must be number, for example 26.43");
		}
	}
}
