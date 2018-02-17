package com.homeacc.mapper;

import java.util.ArrayList;
import java.util.List;

import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.entity.BudgetRecord;

public class Mapper {

	public static List<BudgetRecordDTO> mapBudgetRecordsListToDtoList(List<BudgetRecord> records) {
		List<BudgetRecordDTO> result = new ArrayList<>();
		for(BudgetRecord record : records){
			result.add(mapBudgetRecordToDto(record));
		}
		return result;
	}

	public static BudgetRecordDTO mapBudgetRecordToDto(BudgetRecord record) {
		BudgetRecordDTO dto = new BudgetRecordDTO();
		dto.setId(record.getId());
		dto.setUserName(record.getUsers().getName());
		dto.setCategoryName(record.getCategory().getName());
		dto.setCreated(record.getCreated());
		dto.setBudgetType(record.getBudgetType().getCode());
		dto.setAmount(record.getAmount());
		return dto;
	}

}
