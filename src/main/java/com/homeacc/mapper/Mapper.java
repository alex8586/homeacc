package com.homeacc.mapper;

import java.util.ArrayList;
import java.util.List;

import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.entity.BudgetRecord;

public class Mapper {

	public static List<BudgetRecordDTO> mapIncomeListToDtoList(List<BudgetRecord> incomes) {
		List<BudgetRecordDTO> result = new ArrayList<>();
		for(BudgetRecord income : incomes){
			result.add(mapIncomeToDTO(income));
		}
		return result;
	}

	public static BudgetRecordDTO mapIncomeToDTO(BudgetRecord income) {
		BudgetRecordDTO dto = new BudgetRecordDTO();
		dto.setId(income.getId());
		dto.setUserName(income.getUsers().getName());
		dto.setCategoryName(income.getCategory().getName());
		dto.setCreated(income.getCreated());
		dto.setBudgetType(income.getBudgetType().getCode());
		dto.setAmount(income.getAmount());
		return dto;
	}

}
