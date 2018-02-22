package com.homeacc.controler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.classifier.BudgetTypeEnum;
import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.entity.BudgetType;
import com.homeacc.service.BudgetRecordsService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;

@Component
public class ChartControler {

	@FXML
	private ComboBox<String> selectBudgetType;

	@FXML
	private BarChart<String, Number> barChart;

	@Autowired
	private BudgetRecordsService budgetRecordsService;

	@FXML
	public void initialize() {
		loadBarChart();
		loadSelectionBudgetType();
	}

	private void loadBarChart() {
		ObservableList<BudgetRecordDTO> records = budgetRecordsService.getAll();
		Map<String, BigDecimal> map = getRec(records, BudgetTypeEnum.EXPENSES);

		XYChart.Series<String, Number> series = new XYChart.Series<>();

		for(Map.Entry<String, BigDecimal> entry : map.entrySet()) {
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}

		barChart.getData().add(series);
		barChart.setLegendVisible(false);
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

	private Map<String, BigDecimal> getRec(ObservableList<BudgetRecordDTO> records, BudgetTypeEnum type) {
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

	public void switchBudgetType() {
		String type = selectBudgetType.getSelectionModel().getSelectedItem();

		ObservableList<BudgetRecordDTO> records = budgetRecordsService.getAll();
		Map<String, BigDecimal> map = getRec(records, BudgetTypeEnum.valueOf(type));

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		for(Map.Entry<String, BigDecimal> entry : map.entrySet()) {
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}
		barChart.getData().clear();
		barChart.getData().add(series);
	}
}
