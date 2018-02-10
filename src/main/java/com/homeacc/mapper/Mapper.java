package com.homeacc.mapper;

import java.util.ArrayList;
import java.util.List;

import com.homeacc.dto.IncomeDTO;
import com.homeacc.entity.BudgetRecord;

public class Mapper {

	public static List<IncomeDTO> mapIncomeListToDtoList(List<BudgetRecord> incomes) {
		List<IncomeDTO> result = new ArrayList<>();
		for(BudgetRecord income : incomes){
			result.add(mapIncomeToDTO(income));
		}
		return result;
	}

	public static IncomeDTO mapIncomeToDTO(BudgetRecord income) {
		IncomeDTO dto = new IncomeDTO();
		dto.setId(income.getId());
		dto.setUserName(income.getUsers().getName());
		dto.setCategoryName(income.getCategory().getName());
		dto.setCreated(income.getCreated());
		dto.setAmount(income.getAmount());
		return dto;
	}
}
