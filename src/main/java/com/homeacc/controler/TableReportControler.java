package com.homeacc.controler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.classifier.BudgetTypeEnum;
import com.homeacc.classifier.MonthEnum;
import com.homeacc.dto.MonthSumDTO;
import com.homeacc.dto.TableReportDTO;
import com.homeacc.entity.BudgetType;
import com.homeacc.service.BudgetRecordsService;
import com.homeacc.service.impl.TableReportManagerImpl;
import com.homeacc.utils.DateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@Component
public class TableReportControler extends ChangeRecordControler {

	@FXML
	private VBox vBox;
	@FXML
	private ComboBox<String> selectBudgetType;

	private TableView<TableReportDTO> table;
	private ObservableList<TableReportDTO> chart = FXCollections.observableArrayList();

	@Autowired
	private TableReportManagerImpl tableReportManager;
	@Autowired
	private BudgetRecordsService budgetRecordsService;

	@FXML
    public void initialize() {
		loadSelectionBudgetType();
		initializeTable();
		fillReportTableDefault();
		VBox.setVgrow(table, Priority.ALWAYS);
	}

	private void initializeTable() {
		this.table = new TableView<TableReportDTO>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setMinWidth(780);
		VBox.setMargin(table, new Insets(10));
		vBox.getChildren().add(table);
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

	public void fillReportTableDefault() {
		fillReportTable(BudgetTypeEnum.EXPENSES.getId());
	}

	public void show() {
		long budgetTypeId = BudgetTypeEnum.valueOf(selectBudgetType.getSelectionModel().getSelectedItem()).getId();
		fillReportTable(budgetTypeId);
	}

	public void fillReportTable(long budgetTypeId) {
		List<MonthSumDTO> list = tableReportManager.getDataForTableReport(groupId, budgetTypeId);
		Map<String, TableReportDTO> map = getFilledTableReportDto(list);

		chart.clear();
		table.getColumns().clear();

		TableColumn<TableReportDTO, String> category = new TableColumn<TableReportDTO, String>();
		category.setText("category");
		category.setCellValueFactory(new PropertyValueFactory<TableReportDTO, String>("category"));
		category.setMinWidth(100);
		table.getColumns().add(category);

		List<MonthEnum> monthList = MonthEnum.getMonthsForOneYearInOrder(DateUtils.getCurrentMonth());
		for (MonthEnum month : monthList) {
			TableColumn<TableReportDTO, BigDecimal> monthColumn = new TableColumn<TableReportDTO, BigDecimal>();
			monthColumn.setText(month.name().toLowerCase());
			monthColumn.setCellValueFactory(new PropertyValueFactory<TableReportDTO, BigDecimal>(month.name().toLowerCase()));
			table.getColumns().add(monthColumn);
		}

		chart.addAll(map.values());
		table.setItems(chart);
	}

	private Map<String, TableReportDTO> getFilledTableReportDto(List<MonthSumDTO> list) {
		Map<String, TableReportDTO> map = new HashMap<String, TableReportDTO>();
		for (MonthSumDTO dto : list) {
			if (map.get(dto.getCategoryName()) == null) {
				TableReportDTO reportDto = new TableReportDTO();
				reportDto.setCategory(dto.getCategoryName());
				map.put(dto.getCategoryName(), reportDto);
			}
			TableReportDTO reportDto = map.get(dto.getCategoryName());
			long month = dto.getMonthNum();
			if (month == 1) {
				reportDto.setJanuary(dto.getSum());
			} else if (month == 2) {
				reportDto.setFebruary(dto.getSum());
			} else if (month == 3) {
				reportDto.setMarch(dto.getSum());
			} else if (month == 4) {
				reportDto.setApril(dto.getSum());
			} else if (month == 5) {
				reportDto.setMay(dto.getSum());
			} else if (month == 6) {
				reportDto.setJune(dto.getSum());
			} else if (month == 7) {
				reportDto.setJuly(dto.getSum());
			} else if (month == 8) {
				reportDto.setAugust(dto.getSum());
			} else if (month == 9) {
				reportDto.setSeptember(dto.getSum());
			} else if (month == 10) {
				reportDto.setOctober(dto.getSum());
			} else if (month == 11) {
				reportDto.setNovember(dto.getSum());
			} else if (month == 12) {
				reportDto.setDecember(dto.getSum());
			}
			map.put(dto.getCategoryName(), reportDto);
		}
		return map;
	}
}
