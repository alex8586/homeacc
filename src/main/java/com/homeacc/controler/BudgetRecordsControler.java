package com.homeacc.controler;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.appconst.AppConst;
import com.homeacc.dto.IncomeDTO;
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
	private TextField txtIncomeAmount;

	@FXML
	private TableView<IncomeDTO> tvIncome;
	@FXML
	private TableColumn<IncomeDTO, Long> tcId;
	@FXML
	private TableColumn<IncomeDTO, String> tcUser;
	@FXML
	private TableColumn<IncomeDTO, String> tcCategory;
	@FXML
	private TableColumn<IncomeDTO, Date> tcDate;
	@FXML
	private TableColumn<IncomeDTO, BigDecimal> tcAmount;

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
	private ObservableList<IncomeDTO> recordList = FXCollections.observableArrayList();

	@FXML
    public void initialize() {
		loadUserComboBox();
		loadCategoryComboBox();
		loadIncomeTable();
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
		tcId.setCellValueFactory(new PropertyValueFactory<IncomeDTO, Long>("id"));
        tcUser.setCellValueFactory(new PropertyValueFactory<IncomeDTO, String>("userName"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<IncomeDTO, String>("categoryName"));
        tcDate.setCellValueFactory(new PropertyValueFactory<IncomeDTO, Date>("created"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<IncomeDTO, BigDecimal>("amount"));
        recordList.addAll(budgetRecordsService.getAll());
        tvIncome.setItems(recordList);
	}

	private void handleEventForEditIncomeModalWindow() {
		tvIncome.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					IncomeDTO record = tvIncome.getSelectionModel().getSelectedItem();
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
			String amount = txtIncomeAmount.getText();
			budgetRecordsService.saveOrUpdate(null,userName, categoryName, date, amount);
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
