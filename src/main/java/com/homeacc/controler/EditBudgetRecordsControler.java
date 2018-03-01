package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.EDIT_BUDGET_RECORD_PATH;
import static com.homeacc.appconst.AppConst.TEXT_RED;
import static com.homeacc.appconst.AppFieldsConst.EDIT_BUDGET_RECORD_WINDOW_TITLE;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.dto.BudgetRecordDTO;
import com.homeacc.entity.BudgetType;
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
import javafx.stage.Window;
import javafx.util.StringConverter;

@Component
public class EditBudgetRecordsControler extends ChangeRecordControler {

	@FXML
	private ChoiceBox<Users> cbxUser;
	@FXML
	private ChoiceBox<Category> cbxCategory;
	@FXML
	private ChoiceBox<BudgetType> cbxBudgetType;
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
	private ObservableList<BudgetType> budgetTypeList;
	private long recordId;

	public void openModal(Window window, BudgetRecordDTO record, ObservableList<Users> userList, ObservableList<Category> categoryList, ObservableList<BudgetType> budgetTypeList) throws IOException {
		FXMLLoader loader = springLoader.getLoader(EDIT_BUDGET_RECORD_PATH);
		AnchorPane anchorPane = (AnchorPane) loader.load();

		Stage stage = new Stage();
        stage.setTitle(EDIT_BUDGET_RECORD_WINDOW_TITLE);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.centerOnScreen();

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        recordId = record.getId();
        this.userList = userList;
        this.categoryList = categoryList;
        this.budgetTypeList = budgetTypeList;

        loadCategoryComboBox(record.getCategoryName());
        loadUserComboBox(record.getUserName());
        loadBudgetType(record.getBudgetType());
    	date.setValue(DateUtils.dateToLocalDate(record.getCreated()));
        amount.setText(record.getAmount().toString());

        stage.initOwner(window);
        stage.showAndWait();
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

	public void loadBudgetType(String budgetType) {
		cbxBudgetType.setItems(budgetTypeList);
		for (BudgetType type : budgetTypeList) {
			if (type.getCode().equals(budgetType)) {
				cbxBudgetType.getSelectionModel().select(type);
			}
		}
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

	@FXML
	public void editBudgetRecord() {
		try {
			BudgetRecordValidator.validateFields(cbxUser.getSelectionModel().getSelectedItem(),
					cbxCategory.getSelectionModel().getSelectedItem(), date.getValue(),
					amount.getText());
			BudgetRecordValidator.validateAmount(amount.getText());

			recordsService.saveOrUpdate(recordId, cbxUser.getSelectionModel().getSelectedItem().getName(),
					cbxCategory.getSelectionModel().getSelectedItem().getName(), date.getValue(),
					cbxBudgetType.getSelectionModel().getSelectedItem(), amount.getText());
			recordsControler.loadBudgetRecordsTable();
			recordsChanged = true;
			closeWindow();
		} catch (EmptyFieldsException e) {
			error.setText(e.getMessage());
			error.setStyle(TEXT_RED);
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
		    recordsService.deleteBudgetRecord(recordId);
		    recordsControler.loadBudgetRecordsTable();
		    recordsChanged = true;
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
