package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.EMPTY_STRING;
import static com.homeacc.appconst.AppConst.MANAGE_CATEGORY_PATH;
import static com.homeacc.appconst.AppConst.TEXT_RED;
import static com.homeacc.appconst.AppFieldsConst.MANAGE_CATEGORY_WINDOW_TITLE;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.exception.EntityExistException;
import com.homeacc.main.SpringFXMLLoader;
import com.homeacc.service.CategoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

@Component
public class ManageCategoryControler {

	@FXML
	private Label error;
	@FXML
	private TextField txtAddCategory;

	@FXML
	private TableView<Category> tvCategory;
	@FXML
	private TableColumn<Category, Long> tcId;
	@FXML
	private TableColumn<Category, String> tcName;

	@Autowired
	private SpringFXMLLoader springLoader;
	@Autowired
	private BudgetRecordsControler budgetRecordsControler;
	@Autowired
	private CategoryService categoryService;

	private ObservableList<Category> categoryList = FXCollections.observableArrayList();

	@FXML
    public void initialize() {
		loadCategoryList();
	}

	private void loadCategoryList() {
		tcId.setCellValueFactory(new PropertyValueFactory<Category, Long>("id"));
		tcName.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
		tcName.setCellFactory(TextFieldTableCell.forTableColumn());
		tcName.setOnEditCommit(new EventHandler<CellEditEvent<Category, String>>() {
			@Override
			public void handle(CellEditEvent<Category, String> event) {
				String oldName = event.getTableView().getItems().get(event.getTablePosition().getRow()).getName();
				String newName = event.getNewValue();
				if (oldName.equals(newName)) {
					return;
				}
				Category category = editCategory(oldName, newName);
				((Category) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(category.getName());
			}
		});
		reloadCategoryList();
	}

	public void openModal(Window window) throws IOException {
		FXMLLoader loader = springLoader.getLoader(MANAGE_CATEGORY_PATH);
		AnchorPane anchorPane = (AnchorPane) loader.load();
		Stage stage = new Stage();
        stage.setTitle(MANAGE_CATEGORY_WINDOW_TITLE);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.centerOnScreen();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.initOwner(window);
        stage.showAndWait();
	}

	@FXML
	public void addCategory() {
		if (StringUtils.isBlank(txtAddCategory.getText())) {
			createError("Category name must be filled");
		} else if (txtAddCategory.getText().length() > 30) {
			createError("Category name must be shoter than 30 symbols");
		} else {
			try {
				categoryService.createCategory(txtAddCategory.getText());
				loadCategoryList();
				clearError();
				budgetRecordsControler.loadCategoryComboBox();
			} catch (EntityExistException e) {
				createError(e.getMessage());
			}
		}
	}

	@FXML
	public Category editCategory(String oldName, String newName) {
		Category category = categoryService.getByName(oldName);
		category.setName(newName);
		Category updated = categoryService.updateCategory(category);
		reloadCategoryList();
		reloadIncomeTab();
		clearError();
		return updated;
	}

	@FXML
	public void deleteCategory() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Delete category");
		alert.setHeaderText(null);
		alert.setContentText("Attention! All records in system will be deleted with such category.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Category category = tvCategory.getSelectionModel().getSelectedItem();
			if (category == null) {
				createError("Choose which category you want to delete");
				alert.close();
				return;
			}
			categoryService.deleteCategory(category);
			reloadCategoryList();
			reloadIncomeTab();
		} else {
			alert.close();
		}
	}

	private void reloadCategoryList() {
		categoryList.clear();
		categoryList.addAll(categoryService.getAll());
		tvCategory.setItems(categoryList);
	}

	private void createError(String message) {
		error.setText(message);
		error.setStyle(TEXT_RED);
	}

	private void clearError() {
		error.setText(EMPTY_STRING);
		error.setStyle(EMPTY_STRING);
	}

	private void reloadIncomeTab() {
		budgetRecordsControler.loadCategoryComboBox();
		budgetRecordsControler.loadBudgetRecordsTable();
	}

}
