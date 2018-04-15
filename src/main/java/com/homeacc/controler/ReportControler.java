package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.EMPTY_STRING;
import static com.homeacc.appconst.AppConst.RECORDS_FONT;
import static com.homeacc.appconst.AppConst.RECORDS_FONT_UNDERLINE;
import static com.homeacc.appconst.AppConst.TITLE_FONT_SIZE;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.classifier.BudgetTypeEnum;
import com.homeacc.service.ReportManager;
import com.homeacc.utils.DateUtils;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

@Component
public class ReportControler extends ChangeRecordControler {

	@FXML
	private VBox root;

	@Autowired
	private ReportManager reportManager;

	@FXML
	public void initialize() {
		loadReport();
	}

	public void loadReport() {
		root.getChildren().clear();
		root.setSpacing(30);

		root.getChildren().add(constructTitle());

		GridPane grid = new GridPane();
		grid.getColumnConstraints().add(new ColumnConstraints(200));
		grid.getColumnConstraints().add(new ColumnConstraints(200));
		grid.setAlignment(Pos.CENTER);

		Map<String, Map<String, BigDecimal>> map = reportManager.getReport(null, null, 0, groupId);
		int row = 0;
		Map<String, BigDecimal> incomes = map.get(BudgetTypeEnum.INCOME.name());
		if (incomes != null && !incomes.isEmpty()) {
			addBudgetTypeTitile(grid, row, BudgetTypeEnum.INCOME);
			row++;

			row = addRecords(grid, incomes, row);
		}

		grid.add(new Text(EMPTY_STRING), 0, row);
		row++;

		Map<String, BigDecimal> expenses = map.get(BudgetTypeEnum.EXPENSES.name());
		if (expenses != null && !expenses.isEmpty()) {
			addBudgetTypeTitile(grid, row, BudgetTypeEnum.EXPENSES);
			row++;

			row = addRecords(grid, expenses, row);
		}

		grid.add(new Text(EMPTY_STRING), 0, row);
		row++;

		addBalance(grid, row, incomes, expenses);
		root.getChildren().add(grid);
	}

	private Text constructTitle() {
		Date start = DateUtils.getStartOfMonthByDate(new Date());
		Date end = DateUtils.getEndOfMonthByDate(new Date());

		Text title = new Text();
		title.setText("Report from " + DateUtils.format(start) + " till " + DateUtils.format(end));
		title.setStyle(TITLE_FONT_SIZE);
		return title;
	}

	private void addBudgetTypeTitile(GridPane grid, int row, BudgetTypeEnum type) {
		Text title = new Text();
		title.setStyle(RECORDS_FONT_UNDERLINE);
		title.setText(type.name() + ":");
		grid.add(title, 0, row);
		grid.add(new Text(EMPTY_STRING), 1, row);
	}

	private int addRecords(GridPane grid, Map<String, BigDecimal> records, int row) {
		for (Map.Entry<String, BigDecimal> entry : records.entrySet()) {
			Text category = new Text();
			category.setStyle(RECORDS_FONT);
			category.setText(entry.getKey());
			grid.add(category, 0, row);

			Text amount = new Text();
			amount.setStyle(RECORDS_FONT);
			amount.setText(entry.getValue().toString());
			grid.add(amount, 1, row);

			row++;
		}
		return row;
	}

	private void addBalance(GridPane grid, int row, Map<String, BigDecimal> incomes, Map<String, BigDecimal> expenses) {
		BigDecimal income = BigDecimal.ZERO;
		for(Map.Entry<String, BigDecimal> entry : incomes.entrySet()) {
			income = income.add(entry.getValue());
		}

		BigDecimal expense = BigDecimal.ZERO;
		for(Map.Entry<String, BigDecimal> entry : expenses.entrySet()) {
			expense = expense.add(entry.getValue());
		}

		Text total = new Text("Total:");
		total.setStyle(RECORDS_FONT_UNDERLINE);
		grid.add(total, 0, row);
		row++;

		Text textIncome = new Text("Income");
		textIncome.setStyle(RECORDS_FONT);
		grid.add(textIncome, 0, row);
		Text amountIncome = new Text(income.toString());
		amountIncome.setStyle(RECORDS_FONT);
		grid.add(amountIncome, 1, row);
		row++;

		Text textExpenses = new Text("Expenses");
		textExpenses.setStyle(RECORDS_FONT);
		grid.add(textExpenses, 0, row);
		Text amountExpenses = new Text(expense.toString());
		amountExpenses.setStyle(RECORDS_FONT);
		grid.add(amountExpenses, 1, row);
		row++;

		Text textBalance = new Text("Balanse");
		textBalance.setStyle(RECORDS_FONT);
		grid.add(textBalance, 0, row);
		Text amountBalance = new Text(income.subtract(expense).toString());
		amountBalance.setStyle(RECORDS_FONT);
		grid.add(amountBalance, 1, row);

	}
}
