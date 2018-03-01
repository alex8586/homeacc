package com.homeacc.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.classifier.BudgetTypeEnum;

@Component
public class MainControler extends ChangeRecordControler {

	@Autowired
	private ChartControler chartControler;

	public void reloadChart() {
		if (recordsChanged) {
			chartControler.loadBarChart(BudgetTypeEnum.EXPENSES, null, null);
			recordsChanged = false;
		}
	}
}
