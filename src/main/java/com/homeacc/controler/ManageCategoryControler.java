package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.MANAGE_CATEGORY_PATH;
import static com.homeacc.appconst.AppFieldsConst.MANAGE_CATEGORY_WINDOW_TITLE;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.exception.ValidationException;
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
import javafx.scene.control.SelectionMode;
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
public class ManageCategoryControler extends ChangeRecordControler {

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
		tcName.setCellFactory(TextFieldTableCell.<Category>forTableColumn());
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
		tvCategory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
			createError(error, "Category name must be filled");
		} else if (txtAddCategory.getText().length() > 30) {
			createError(error, "Category name must be shoter than 30 symbols");
		} else {
			try {
				categoryService.createCategory(txtAddCategory.getText());
				loadCategoryList();
				clearError(error);
				budgetRecordsControler.loadCategoryComboBox();
			} catch (ValidationException e) {
				createError(error, e.getMessage());
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
		clearError(error);
		recordsChanged = true;
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
			ObservableList<Category> categories =  tvCategory.getSelectionModel().getSelectedItems();
			if (categories == null || categories.isEmpty()) {
				createError(error, "Choose which category you want to delete");
				alert.close();
				return;
			}
			for (Category category : categories) {
				categoryService.deleteCategory(category);
			}
			reloadCategoryList();
			reloadIncomeTab();
			recordsChanged = true;
		} else {
			alert.close();
		}
	}

	private void reloadCategoryList() {
		categoryList.clear();
		categoryList.addAll(categoryService.getAll());
		tvCategory.setItems(categoryList);
	}

	private void reloadIncomeTab() {
		budgetRecordsControler.loadCategoryComboBox();
		budgetRecordsControler.loadBudgetRecordsTable();
	}

}
