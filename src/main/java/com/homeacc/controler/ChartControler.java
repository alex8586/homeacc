package com.homeacc.controler;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.appconst.AppFieldsConst;
import com.homeacc.classifier.BudgetTypeEnum;
import com.homeacc.classifier.MonthEnum;
import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.entity.BudgetType;
import com.homeacc.service.BudgetRecordsService;
import com.homeacc.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

@Component
public class ChartControler extends ChangeRecordControler {

	@FXML
	private ComboBox<String> selectBudgetType;

	@FXML
	private BarChart<String, BigDecimal> barChart;

	@FXML
	private DatePicker dateFrom;
	@FXML
	private DatePicker dateTo;

	@FXML
	private ComboBox<MonthEnum> month;

	@FXML
	private Label info;

	private ObservableList<MonthEnum> monthList = FXCollections.observableArrayList();

	@Autowired
	private BudgetRecordsService budgetRecordsService;

	@FXML
	public void initialize() {
		loadBarChart(BudgetTypeEnum.EXPENSES, null, null, 0);
		loadSelectionBudgetType();
		loadMonth();
		clearError(info);
	}

	public void loadBarChart(BudgetTypeEnum budgetType, Date from, Date to, int monthNumber) {
		List<BudgetRecordDTO> records = budgetRecordsService
				.getBudgetRecordsByDateAndBudgetType(groupId, budgetType.getId(), from, to, monthNumber);

		Map<String, BigDecimal> map = getRecordsForChart(records, budgetType);

		XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();

		for(Map.Entry<String, BigDecimal> entry : map.entrySet()) {
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}

		barChart.getData().clear();
		barChart.getData().add(series);
		barChart.setLegendVisible(false);
		barChart.setTitle(getTitle(budgetType, from, to));
	}

	private void loadSelectionBudgetType() {
		ObservableList<String> types = FXCollections.observableArrayList();
		List<BudgetType> list = budgetRecordsService.getAllBudgetType();
		for (BudgetType type : list) {
			types.add(type.getCode());
		}
		selectBudgetType.setValue(BudgetTypeEnum.EXPENSES.getCode());
		selectBudgetType.setItems(types);
	}

	private void loadMonth() {
		List<MonthEnum> months = Arrays.asList(MonthEnum.values());
		monthList.addAll(months);
		month.setItems(monthList);
		month.getSelectionModel().select(0);
	}

	public Label getInfo() {
		return info;
	}

	public void filterRecordsInBarChart() {
		clearError(info);
		String type = selectBudgetType.getSelectionModel().getSelectedItem();
		Date from = dateFrom.getValue() == null ? null : DateUtils.localDateToDate(dateFrom.getValue());
		Date to = dateTo.getValue() == null ? null : DateUtils.localDateToDate(dateTo.getValue());
		int monthNumber = month.getSelectionModel().getSelectedItem().getMonthNumber();

		if ((from != null || to != null) && monthNumber != 0) {
			createError(info, "select search by dates or by whole month");
			return;
		}
		loadBarChart(BudgetTypeEnum.valueOf(type), from, to, monthNumber);
		dateFrom.setValue(null);
		dateTo.setValue(null);
	}

	private Map<String, BigDecimal> getRecordsForChart(List<BudgetRecordDTO> records, BudgetTypeEnum type) {
		Map<String, BigDecimal> map = new HashMap<>();
		for(BudgetRecordDTO dto : records) {
			if (dto.getBudgetType().equals(type.getCode())) {
				if (map.containsKey(dto.getCategoryName())) {
					BigDecimal amount = map.get(dto.getCategoryName());
					amount = amount.add(dto.getAmount());
					map.put(dto.getCategoryName(), amount);
				}else {
					map.put(dto.getCategoryName(), dto.getAmount());
				}
			}
		}
		return map;
	}

	private String getTitle(BudgetTypeEnum budgetType, Date from, Date to) {
		String title = budgetType.getCode();
		if (budgetType.getCode().equals(BudgetTypeEnum.INCOME.getCode())) {
			title = AppFieldsConst.INCOME;
		} else if (budgetType.getCode().equals(BudgetTypeEnum.EXPENSES.getCode())) {
			title = AppFieldsConst.EXPENSES;
		}
		if (from != null) {
			title = title + "  from  " + DateUtils.format(from);
		}
		if (to != null) {
			title = title + "  to  " + DateUtils.format(to);
		}
		return title;
	}

}
