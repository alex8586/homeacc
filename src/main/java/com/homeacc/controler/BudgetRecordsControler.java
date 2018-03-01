package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.EMPTY_STRING;
import static com.homeacc.appconst.AppConst.TEXT_RED;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.appconst.AppFieldsConst;
import com.homeacc.classifier.BudgetTypeEnum;
import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.dto.BudgetRecordsCriteriaFilter;
import com.homeacc.dto.BudgetRecordsCriteriaFilterBuilder;
import com.homeacc.entity.BudgetType;
import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.exception.EmptyFieldsException;
import com.homeacc.service.BudgetRecordsService;
import com.homeacc.service.CategoryService;
import com.homeacc.service.UserService;
import com.homeacc.utils.DateUtils;
import com.homeacc.validation.BudgetRecordValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

@Component
public class BudgetRecordsControler extends ChangeRecordControler {

	@FXML
	private ComboBox<Users> cbxUser;
	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private DatePicker recordDate;
	@FXML
	private ComboBox<BudgetType> cbxBudgetType;
	@FXML
	private TextField txtRecordAmount;

	@FXML
	private ComboBox<Users> filterSelectUser;
	@FXML
	private ComboBox<Category> filterSelectCategory;
	@FXML
	private ComboBox<BudgetType> filterSelectBudgetType;
	@FXML
	private DatePicker filterDateFrom;
	@FXML
	private DatePicker filterDateTo;
	@FXML
	private TextField filterAmountFrom;
	@FXML
	private TextField filterAmountTo;

	@FXML
	private TableView<BudgetRecordDTO> tvBudgetRecords;
	@FXML
	private TableColumn<BudgetRecordDTO, Long> tcId;
	@FXML
	private TableColumn<BudgetRecordDTO, String> tcUser;
	@FXML
	private TableColumn<BudgetRecordDTO, String> tcCategory;
	@FXML
	private TableColumn<BudgetRecordDTO, Date> tcDate;
	@FXML
	private TableColumn<BudgetRecordDTO, String> tcBudgetType;
	@FXML
	private TableColumn<BudgetRecordDTO, BigDecimal> tcAmount;

	@FXML
	private Label periodIncome;
	@FXML
	private Label periodExpenses;
	@FXML
	private Label periodBalance;

	@FXML
	private Label createRecordError;
	@FXML
	private Label createFilterError;

	@Autowired
	private EditBudgetRecordsControler editBudgetRecordsControler;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private BudgetRecordsService budgetRecordsService;

	private ObservableList<Users> userList = FXCollections.observableArrayList();
	private ObservableList<Category> categoryList = FXCollections.observableArrayList();
	private ObservableList<BudgetRecordDTO> recordList = FXCollections.observableArrayList();
	private ObservableList<BudgetType> recordTypeList = FXCollections.observableArrayList();

	private ObservableList<Users> filterUserList = FXCollections.observableArrayList();
	private ObservableList<Category> filterCategoryList = FXCollections.observableArrayList();
	private ObservableList<BudgetType> filterRecordTypeList = FXCollections.observableArrayList();

	@FXML
    public void initialize() {
		loadUserComboBox();
		loadCategoryComboBox();
		loadBudgetRecordsTable();
		loadBudgetType();
		handleEventForEditBudgetRecordModalWindow();
    }

	public void loadUserComboBox() {
		userList.clear();
		filterUserList.clear();
		List<Users> users = userService.getAll();
		userList.addAll(users);
		cbxUser.setItems(userList);

		Users user = new Users();
		user.setName(AppFieldsConst.ALL_USERS);
		filterUserList.add(user);
		filterUserList.addAll(users);
		filterSelectUser.setItems(filterUserList);

		StringConverter<Users> converter = new StringConverter<Users>() {
            @Override
            public String toString(Users user) {
                return user.getName();
            }

            @Override
            public Users fromString(String string) {
                return null;
            }
        };
		cbxUser.setConverter(converter);
		filterSelectUser.setConverter(converter);
	}

	public void loadCategoryComboBox() {
		categoryList.clear();
		filterCategoryList.clear();
		List<Category> categories =  categoryService.getAll();
		categoryList.addAll(categories);
		cbxCategory.setItems(categoryList);

		Category category = new Category();
		category.setName(AppFieldsConst.ALL_CATEGORIES);
		filterCategoryList.add(category);
		filterCategoryList.addAll(categories);
		filterSelectCategory.setItems(filterCategoryList);

		StringConverter<Category> converter = new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        };
		cbxCategory.setConverter(converter);
		filterSelectCategory.setConverter(converter);
	}

	public void loadBudgetRecordsTable() {
		recordList.clear();
		tcId.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, Long>("id"));
        tcUser.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, String>("userName"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, String>("categoryName"));
        tcDate.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, Date>("created"));
        tcBudgetType.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, String>("budgetType"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, BigDecimal>("amount"));
        recordList.addAll(budgetRecordsService.getAll());
        tvBudgetRecords.setItems(recordList);

        countBalance();
	}

	private void countBalance() {
		BigDecimal income = BigDecimal.ZERO;
		BigDecimal expenses = BigDecimal.ZERO;

		for (BudgetRecordDTO record : recordList) {
			if (record.getBudgetType().equals(BudgetTypeEnum.INCOME.getCode())) {
				income = income.add(record.getAmount());
			} else if (record.getBudgetType().equals(BudgetTypeEnum.EXPENSES.getCode())) {
				expenses = expenses.add(record.getAmount());
			}
		}

		BigDecimal balance = income.subtract(expenses);
		periodIncome.setText(income.toString());
		periodExpenses.setText(expenses.toString());
		periodBalance.setText(balance.toString());

		periodBalance.setStyle(balance.compareTo(BigDecimal.ZERO) < 0 ? TEXT_RED : EMPTY_STRING);
	}

	public void loadBudgetType() {
		List<BudgetType> types = budgetRecordsService.getAllBudgetType();
		recordTypeList.addAll(types);
		cbxBudgetType.setItems(recordTypeList);

		BudgetType type = new BudgetType();
		type.setCode(BudgetTypeEnum.ALL.getCode());
		filterRecordTypeList.add(type);
		filterRecordTypeList.addAll(types);
		filterSelectBudgetType.setItems(filterRecordTypeList);

		StringConverter<BudgetType> converter = new StringConverter<BudgetType>() {
            @Override
            public String toString(BudgetType type) {
                return type.getCode();
            }

            @Override
            public BudgetType fromString(String string) {
                return null;
            }
        };
        cbxBudgetType.setConverter(converter);
        filterSelectBudgetType.setConverter(converter);
	}

	private void handleEventForEditBudgetRecordModalWindow() {
		tvBudgetRecords.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					BudgetRecordDTO record = tvBudgetRecords.getSelectionModel().getSelectedItem();
					try {
						editBudgetRecordsControler.openModal(tvBudgetRecords.getScene().getWindow(), record, userList,
								categoryList, recordTypeList);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void saveRecord() {
		try {
			BudgetRecordValidator.validateFields(cbxUser.getSelectionModel().getSelectedItem(),
					cbxCategory.getSelectionModel().getSelectedItem(), recordDate.getValue(),
					txtRecordAmount.getText());
			BudgetRecordValidator.validateAmount(txtRecordAmount.getText());

			String userName = cbxUser.getSelectionModel().getSelectedItem().getName();
			String categoryName = cbxCategory.getSelectionModel().getSelectedItem().getName();
			LocalDate date = recordDate.getValue();
			BudgetType budgetType = cbxBudgetType.getSelectionModel().getSelectedItem();
			String amount = txtRecordAmount.getText();
			budgetRecordsService.saveOrUpdate(null,userName, categoryName, date, budgetType, amount);
			loadBudgetRecordsTable();
			clearErrors();
			recordsChanged = true;
		} catch (EmptyFieldsException e) {
			createRecordError.setText(e.getMessage());
			createRecordError.setStyle(TEXT_RED);
		}
	}

	public void filterRecords() {
		if (StringUtils.isNotBlank(filterAmountFrom.getText())) {
			BudgetRecordValidator.validateAmount(filterAmountFrom.getText());
		}
		if (StringUtils.isNotBlank(filterAmountTo.getText())) {
			BudgetRecordValidator.validateAmount(filterAmountTo.getText());
		}

		Users user = filterSelectUser.getSelectionModel().getSelectedItem();
		if (user != null && user.getName().equals(AppFieldsConst.ALL_USERS)) {
			user = null;
		}

		Category category = filterSelectCategory.getSelectionModel().getSelectedItem();
		if (category != null && category.getName().equals(AppFieldsConst.ALL_CATEGORIES)) {
			category = null;
		}

		BudgetType type = filterSelectBudgetType.getSelectionModel().getSelectedItem();
		if (type != null && type.getCode().equals(BudgetTypeEnum.ALL.getCode())) {
			type = null;
		}
		BudgetRecordsCriteriaFilter criteria = BudgetRecordsCriteriaFilterBuilder.createBudgetRecordsCriteraFilter()
				.withUser(user)
				.withCategory(category)
				.withBudgetType(type)
				.withDateFrom(filterDateFrom.getValue() == null ? null : DateUtils.localDateToDate(filterDateFrom.getValue()))
				.withDateTo(filterDateTo.getValue() == null ? null : DateUtils.localDateToDate(filterDateTo.getValue()))
				.withAmountFrom(filterAmountFrom.getText().equals(EMPTY_STRING) ? null : new BigDecimal(filterAmountFrom.getText()))
				.withAmountTo(filterAmountTo.getText().equals(EMPTY_STRING) ? null : new BigDecimal(filterAmountTo.getText()))
				.build();

		List<BudgetRecordDTO> filteredRecords = budgetRecordsService.filterBudgetRecords(criteria);
		recordList.clear();
		recordList.addAll(filteredRecords);

		cleanSearchFields();
	}

	private void cleanSearchFields() {
		filterDateFrom.setValue(null);
		filterDateTo.setValue(null);
		filterAmountFrom.setText(EMPTY_STRING);
		filterAmountTo.setText(EMPTY_STRING);
	}

	private void clearErrors() {
		createRecordError.setText(EMPTY_STRING);
		createRecordError.setStyle(EMPTY_STRING);
		createFilterError.setText(EMPTY_STRING);
		createFilterError.setStyle(EMPTY_STRING);
	}
}
