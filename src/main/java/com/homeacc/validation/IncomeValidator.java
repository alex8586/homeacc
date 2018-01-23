package com.homeacc.validation;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;

import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.exception.EmptyFieldsException;

public class IncomeValidator {

	public static void validateFields(Users user, Category category, LocalDate date, String amount) {
		if (user == null || category == null || StringUtils.isBlank(amount) || date == null) {
			throw new EmptyFieldsException("all fields must be filled");
		}
	}

	public static void validateAmount(String amount) {
		if (!amount.matches("^[0-9]{1,15}([\\.][0-9]{1,2}){0,1}$")) {
			throw new EmptyFieldsException("amount must be number, for example 26.43");
		}
	}
}
