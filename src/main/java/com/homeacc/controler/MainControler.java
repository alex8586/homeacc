package com.homeacc.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.classifier.BudgetTypeEnum;
import com.homeacc.service.BudgetRecordsService;

@Component
public class MainControler {

	@Autowired
	private BudgetRecordsService budgetRecordsService;

	@Autowired
	private ChartControler chartControler;

	public void reloadChart() {
		if (budgetRecordsService.isRecordsChanged()) {
			chartControler.loadBarChart(BudgetTypeEnum.EXPENSES, null, null);
			budgetRecordsService.setIsRecordsChanged(false);
		}
	}
}
