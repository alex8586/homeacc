package com.homeacc.controler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import javafx.scene.control.TextField;
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

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private IncomeService incomeService;

	private ObservableList<Users> userList = FXCollections.observableArrayList();
	private ObservableList<Category> categoryList = FXCollections.observableArrayList();

	@FXML
    public void initialize() {
		loadUserComboBox();
		loadCategoryComboBox();
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
	}
}
