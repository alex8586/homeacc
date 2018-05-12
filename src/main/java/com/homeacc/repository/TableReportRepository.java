package com.homeacc.repository;

import java.util.List;

import com.homeacc.dto.MonthSumDTO;

public interface TableReportRepository {

	List<MonthSumDTO> getDataForTableReport(long groupId, long budgetTypeId);
}
