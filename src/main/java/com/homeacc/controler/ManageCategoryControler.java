package com.homeacc.controler;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.appconst.AppConst;
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
	private IncomeControler incomeControler;
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
				Category category = editCategory(
						event.getTableView().getItems().get(event.getTablePosition().getRow()).getName(),
						event.getNewValue());

				((Category) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(category.getName());

			}
		});
		reloadCategoryList();
	}

	public void openModal(Window window) throws IOException {
		FXMLLoader loader = springLoader.getLoader("/fxml/manage_category.fxml");
		AnchorPane anchorPane = (AnchorPane) loader.load();
		Stage stage = new Stage();
        stage.setTitle("Manage categories");
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
			error.setText("Category name must be filled");
			error.setStyle(AppConst.TEXT_RED);
		} else {
			try {
				categoryService.createCategory(txtAddCategory.getText());
				loadCategoryList();
				clearError();
				incomeControler.loadCategoryComboBox();
			} catch (EntityExistException e) {
				error.setText(e.getMessage());
				error.setStyle(AppConst.TEXT_RED);
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

	private void clearError() {
		error.setText("");
		error.setStyle("");
	}

	private void reloadIncomeTab() {
		incomeControler.loadCategoryComboBox();
		incomeControler.loadIncomeTable();
	}

}
