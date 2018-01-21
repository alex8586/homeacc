package com.homeacc.controler;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.dto.IncomeDTO;
import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.service.CategoryService;
import com.homeacc.service.IncomeService;
import com.homeacc.service.UserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

@Component
public class MainControler {

	@FXML
	private TextField txtAddCategory;
	@FXML
	private TextField txtAddUser;

	@FXML
	private ChoiceBox<Users> cbxUser;
	@FXML
	private ChoiceBox<Category> cbxCategory;
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
	private TableColumn<IncomeDTO, Long> tcAmount;

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private IncomeService incomeService;

	private ObservableList<Users> userList = FXCollections.observableArrayList();
	private ObservableList<Category> categoryList = FXCollections.observableArrayList();
	private ObservableList<IncomeDTO> incomeList = FXCollections.observableArrayList();

	@FXML
    public void initialize() {
		loadUserComboBox();
		loadCategoryComboBox();
		loadIncomeTable();
    }

	private void loadUserComboBox() {
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

	private void loadCategoryComboBox() {
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

	private void loadIncomeTable() {
		tcId.setCellValueFactory(new PropertyValueFactory<IncomeDTO, Long>("id"));
        tcUser.setCellValueFactory(new PropertyValueFactory<IncomeDTO, String>("userName"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<IncomeDTO, String>("categoryName"));
        tcDate.setCellValueFactory(new PropertyValueFactory<IncomeDTO, Date>("created"));
        tcAmount.setCellValueFactory(new PropertyValueFactory<IncomeDTO, Long>("amount"));
        incomeList.addAll(incomeService.getAll());
        tvIncome.setItems(incomeList);
	}

	public void addCategory() {
		categoryService.save(txtAddCategory.getText());
		loadCategoryComboBox();
	}

	public void addUser() {
		userService.save(txtAddUser.getText());
		loadUserComboBox();
	}

	public void saveIncome() {
		String userName = cbxUser.getSelectionModel().getSelectedItem().getName();
		String categoryName = cbxCategory.getSelectionModel().getSelectedItem().getName();
		LocalDate date =  incomeDate.getValue();
		String amount = txtIncomeAmount.getText();
		incomeService.save(userName, categoryName, date, amount);
		loadIncomeTable();
	}
}
