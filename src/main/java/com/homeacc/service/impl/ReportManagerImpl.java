package com.homeacc.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.dto.ReportDTO;
import com.homeacc.repository.ReportRepository;
import com.homeacc.service.ReportManager;

@Component
public class ReportManagerImpl implements ReportManager {

	@Autowired
	private ReportRepository reportRepository;

	public Map<String, Map<String, BigDecimal>> getReport(Date from, Date to, int monthNumber, long groupId) {
		List<ReportDTO> reports = reportRepository.getReportByDate(from, to, monthNumber, groupId);
		Map<String, Map<String, BigDecimal>> result = new HashMap<>();

		for (ReportDTO dto : reports) {
			if (!result.containsKey(dto.getBudgetType())) {
				result.put(dto.getBudgetType(), new HashMap<String, BigDecimal>());
			}
			result.get(dto.getBudgetType()).put(dto.getCategoryName(), dto.getAmount());
		}
		return result;
	}
}
