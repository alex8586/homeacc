package com.homeacc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.dto.MonthSumDTO;
import com.homeacc.repository.TableReportRepository;

@Component
public class TableReportManagerImpl {

	@Autowired
	private TableReportRepository tableReportRepository;

	public List<MonthSumDTO> getDataForTableReport(long groupId, long budgetTypeId) {
		return tableReportRepository.getDataForTableReport(groupId, budgetTypeId);
	}
}
