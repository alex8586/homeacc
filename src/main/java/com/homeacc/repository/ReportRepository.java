package com.homeacc.repository;

import java.util.Date;
import java.util.List;

import com.homeacc.dto.ReportDTO;

public interface ReportRepository {

	List<ReportDTO> getReportByDate(Date from, Date to, int monthNumber, long groupId);
}
