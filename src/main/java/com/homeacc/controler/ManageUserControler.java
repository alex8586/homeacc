package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.EMPTY_STRING;
import static com.homeacc.appconst.AppConst.TEXT_RED;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Users;
import com.homeacc.exception.EntityExistException;
import com.homeacc.main.SpringFXMLLoader;
import com.homeacc.service.UserService;

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
public class ManageUserControler {

	@FXML
	private Label error;
	@FXML
	private TextField txtAddUser;

	@FXML
	private TableView<Users> tvUser;
	@FXML
	private TableColumn<Users, Long> tcId;
	@FXML
	private TableColumn<Users, String> tcName;

	@Autowired
	private SpringFXMLLoader springLoader;
	@Autowired
	private IncomeControler incomeControler;
	@Autowired
	private UserService userService;

	private ObservableList<Users> userList = FXCollections.observableArrayList();

	@FXML
    public void initialize() {
		loadUserList();
	}

	private void loadUserList() {
		tcId.setCellValueFactory(new PropertyValueFactory<Users, Long>("id"));
		tcName.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));
		tcName.setCellFactory(TextFieldTableCell.forTableColumn());
		tcName.setOnEditCommit(new EventHandler<CellEditEvent<Users, String>>() {
			@Override
			public void handle(CellEditEvent<Users, String> event) {
				Users user = editUser(
						event.getTableView().getItems().get(event.getTablePosition().getRow()).getName(),
						event.getNewValue());

				((Users) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setName(user.getName());

			}
		});
		reloadUserList();
	}

	public void openModal(Window window) throws IOException {
		FXMLLoader loader = springLoader.getLoader("/fxml/manage_user.fxml");
		AnchorPane anchorPane = (AnchorPane) loader.load();
		Stage stage = new Stage();
        stage.setTitle("Manage users");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.centerOnScreen();
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.initOwner(window);
        stage.showAndWait();
	}

	@FXML
	public void addUser() {
		if (StringUtils.isBlank(txtAddUser.getText())) {
			createError("User name must be filled");
		} else if (txtAddUser.getText().length() > 15) {
			createError("User name must be shoter than 15 symbols");
		} else {
			try {
				userService.createUser(txtAddUser.getText());
				loadUserList();
				clearError();
				txtAddUser.setText(EMPTY_STRING);
				incomeControler.loadUserComboBox();
			} catch (EntityExistException e) {
				createError(e.getMessage());
			}
		}
	}

	@FXML
	public Users editUser(String oldName, String newName) {
		Users user = userService.getByName(oldName);
		user.setName(newName);
		Users updated = userService.updateUser(user);
		reloadUserList();
		reloadIncomeTab();
		clearError();
		return updated;
	}

	@FXML
	public void deleteUser() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Delete user");
		alert.setHeaderText(null);
		alert.setContentText("Attention! All records in system will be deleted with such user name.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Users user = tvUser.getSelectionModel().getSelectedItem();
			if (user == null) {
				createError("Choose which user you want to delete");
				alert.close();
				return;
			}
			userService.deleteUser(user);
			reloadUserList();
			reloadIncomeTab();
			clearError();
		} else {
			alert.close();
		}
	}

	private void reloadUserList() {
		userList.clear();
		userList.addAll(userService.getAll());
		tvUser.setItems(userList);
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
		incomeControler.loadUserComboBox();
		incomeControler.loadIncomeTable();
	}
}
