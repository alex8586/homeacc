package com.homeacc.controler;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.dto.IncomeDTO;
import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.exception.EmptyFieldsException;
import com.homeacc.main.SpringFXMLLoader;
import com.homeacc.service.BudgetRecordsService;
import com.homeacc.utils.DateUtils;
import com.homeacc.validation.BudgetRecordValidator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Component
public class EditBudgetRecordsControler {

	@FXML
	private ChoiceBox<Users> cbxUser;
	@FXML
	private ChoiceBox<Category> cbxCategory;
	@FXML
	private DatePicker date;
	@FXML
	private TextField amount;

	@FXML
	private Button btnCancel;
	@FXML
	private Button bntDelete;

	@FXML
	private Label error;

	@Autowired
	private SpringFXMLLoader springLoader;
	@Autowired
	private BudgetRecordsService recordsService;
	@Autowired
	private BudgetRecordsControler recordsControler;

	private ObservableList<Users> userList;
	private ObservableList<Category> categoryList;
	private long recordId;

	public void openModal(IncomeDTO income, ObservableList<Users> userList, ObservableList<Category> categoryList) throws IOException {
		FXMLLoader loader = springLoader.getLoader("/fxml/edit_income.fxml");
		AnchorPane anchorPane = (AnchorPane) loader.load();

		Stage stage = new Stage();
        stage.setTitle("Edit or delete records");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.centerOnScreen();

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        recordId = income.getId();
        this.userList = userList;
        this.categoryList = categoryList;

        loadCategoryComboBox(income.getCategoryName());
        loadUserComboBox(income.getUserName());
    	date.setValue(DateUtils.dateToLocalDate(income.getCreated()));
        amount.setText(income.getAmount().toString());

        stage.show();
	}

	private void loadUserComboBox(String userName) {
		cbxUser.setItems(userList);
		for (Users user : userList) {
			if (user.getName().equals(userName)){
				cbxUser.getSelectionModel().select(user);
			}
		}
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

	private void loadCategoryComboBox(String categoryName) {
		cbxCategory.setItems(categoryList);
		for (Category category : categoryList) {
			if (category.getName().equals(categoryName)){
				cbxCategory.getSelectionModel().select(category);
			}
		}
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

	@FXML
	public void editBudgetRecord() {
		try {
			BudgetRecordValidator.validateFields(cbxUser.getSelectionModel().getSelectedItem(),
					cbxCategory.getSelectionModel().getSelectedItem(), date.getValue(),
					amount.getText());
			BudgetRecordValidator.validateAmount(amount.getText());

			recordsService.saveOrUpdate(recordId, cbxUser.getSelectionModel().getSelectedItem().getName(),
					cbxCategory.getSelectionModel().getSelectedItem().getName(), date.getValue(), amount.getText());
			recordsControler.loadIncomeTable();
			closeWindow();
		} catch (EmptyFieldsException e) {
			error.setText(e.getMessage());
			error.setStyle("-fx-text-fill: red");
		}
	}

	@FXML
	public void deleteBudgetRecord() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete record");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure delete this record ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    recordsService.deleteBudgetRecords(recordId);
		    recordsControler.loadIncomeTable();
		    closeWindow();
		} else {
		    alert.close();
		}
	}

	@FXML
    public void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
	}

}
