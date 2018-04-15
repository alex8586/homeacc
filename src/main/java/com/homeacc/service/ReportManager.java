package com.homeacc.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface ReportManager {

	Map<String, Map<String, BigDecimal>> getReport(Date from, Date to, int monthNumber, long groupId);
}
