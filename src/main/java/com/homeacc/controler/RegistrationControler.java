package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.REGISTRATION_PATH;
import static com.homeacc.appconst.AppFieldsConst.REGISTRATION_TITLE;
import static com.homeacc.appconst.AppFieldsConst.SUCCESS_REGISTRATION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.exception.EmptyFieldsException;
import com.homeacc.exception.EntityExistException;
import com.homeacc.exception.ValidationException;
import com.homeacc.main.SpringFXMLLoader;
import com.homeacc.service.AuthenticationManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class RegistrationControler extends ChangeRecordControler {

	private Stage primaryStage;
	private Scene scene;

	@FXML
	private TextField name;
	@FXML
	private PasswordField password;
	@FXML
	private Label error;

	@Autowired
	private SpringFXMLLoader springLoader;
	@Autowired
	private LoginControler loginControler;
	@Autowired
	private AuthenticationManager authenticationManager;

	public void loadRegistrationForm(Stage primaryStage, Scene scene) throws Exception {
		this.primaryStage = primaryStage;
		FXMLLoader loader = springLoader.getLoader(REGISTRATION_PATH);
		Parent root = loader.load();
        this.scene = new Scene(root, scene.getWidth(), scene.getHeight());

        primaryStage.setScene(this.scene);
        primaryStage.setTitle(REGISTRATION_TITLE);
        primaryStage.show();
	}

	public void registration() {
		try {
			authenticationManager.registerGroup(name.getText(), password.getText());
			clearError(error);
			loadLoginPage(SUCCESS_REGISTRATION);
		} catch (EmptyFieldsException | EntityExistException | ValidationException e) {
			createError(error, e.getMessage());
		}
	}

	public void toLoginPage() throws Exception{
		loadLoginPage(null);
	}

	private void loadLoginPage(String message) {
		try {
			clearError(error);
			loginControler.loadLoginForm(primaryStage, message, scene.getWidth(), scene.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
