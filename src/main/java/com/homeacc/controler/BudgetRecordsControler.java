package com.homeacc.controler;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.appconst.AppConst;
import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.entity.BudgetType;
import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.exception.EmptyFieldsException;
import com.homeacc.service.BudgetRecordsService;
import com.homeacc.service.CategoryService;
import com.homeacc.service.UserService;
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
public class BudgetRecordsControler {

	@FXML
	private ComboBox<Users> cbxUser;
	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private DatePicker incomeDate;
	@FXML
	private ComboBox<BudgetType> cbxBudgetType;
	@FXML
	private TextField txtIncomeAmount;

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
	private Label error;

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

	@FXML
    public void initialize() {
		loadUserComboBox();
		loadCategoryComboBox();
		loadIncomeTable();
		loadBudgetType();
		handleEventForEditIncomeModalWindow();
    }

	public void loadUserComboBox() {
		userList = userService.getAll();
		cbxUser.setItems(userList);
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
	}

	public void loadCategoryComboBox() {
		categoryList = categoryService.getAll();
		cbxCategory.setItems(categoryList);
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
	}

	public void loadIncomeTable() {
		recordList.clear();
		tcId.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, Long>("id"));
        tcUser.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, String>("userName"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, String>("categoryName"));
        tcDate.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, Date>("created"));
        tcBudgetType.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, String>("budgetType"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<BudgetRecordDTO, BigDecimal>("amount"));
        recordList.addAll(budgetRecordsService.getAll());
        tvBudgetRecords.setItems(recordList);
	}

	public void loadBudgetType() {
		recordTypeList = budgetRecordsService.getAllBudgetType();
		cbxBudgetType.setItems(recordTypeList);
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
	}

	private void handleEventForEditIncomeModalWindow() {
		tvBudgetRecords.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					BudgetRecordDTO record = tvBudgetRecords.getSelectionModel().getSelectedItem();
					try {
						editBudgetRecordsControler.openModal(record, userList, categoryList);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void saveIncome() {
		try {
			BudgetRecordValidator.validateFields(cbxUser.getSelectionModel().getSelectedItem(),
					cbxCategory.getSelectionModel().getSelectedItem(), incomeDate.getValue(),
					txtIncomeAmount.getText());
			BudgetRecordValidator.validateAmount(txtIncomeAmount.getText());

			String userName = cbxUser.getSelectionModel().getSelectedItem().getName();
			String categoryName = cbxCategory.getSelectionModel().getSelectedItem().getName();
			LocalDate date = incomeDate.getValue();
			BudgetType budgetType = cbxBudgetType.getSelectionModel().getSelectedItem();
			String amount = txtIncomeAmount.getText();
			budgetRecordsService.saveOrUpdate(null,userName, categoryName, date, budgetType, amount);
			loadIncomeTable();
			clearErrors();
		} catch (EmptyFieldsException e) {
			error.setText(e.getMessage());
			error.setStyle(AppConst.TEXT_RED);
		}
	}

	private void clearErrors() {
		error.setText(AppConst.EMPTY_STRING);
		error.setStyle(AppConst.EMPTY_STRING);
	}
}
