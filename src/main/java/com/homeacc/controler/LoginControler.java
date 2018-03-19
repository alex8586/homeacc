package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.LOGIN_PATH;
import static com.homeacc.appconst.AppConst.TEXT_BLUE;
import static com.homeacc.appconst.AppFieldsConst.LOGIN_TITLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.exception.EmptyFieldsException;
import com.homeacc.exception.EntityExistException;
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
public class LoginControler extends ChangeRecordControler {

	private Stage primaryStage;
	private Scene scene;

	@FXML
	private TextField login;
	@FXML
	private PasswordField password;
	@FXML
	private Label error;
	@FXML
	private Label information;

	@Autowired
	private SpringFXMLLoader springLoader;
	@Autowired
	private RegistrationControler registrationControler;
	@Autowired
	private MainControler mainControler;
	@Autowired
	private AuthenticationManager authenticationManager;

	public void loadLoginForm(Stage primaryStage, String message, Double width, Double height) throws Exception {
		this.primaryStage = primaryStage;
		FXMLLoader loader = springLoader.getLoader(LOGIN_PATH);
		Parent root = loader.load();
		Scene scene;
		if (width != null && height != null) {
			scene = new Scene(root, width, height);
		} else {
			scene = new Scene(root);
		}
        this.scene = scene;

        primaryStage.setTitle(LOGIN_TITLE);
        primaryStage.setScene(scene);
        if (message != null) {
        	information.setText(message);
        	information.setStyle(TEXT_BLUE);
        }
        primaryStage.show();
	}

	public void login() throws Exception {
		try {
			authenticationManager.loginGroup(login.getText(), password.getText());
			clearError(error);
			mainControler.loadApplication(primaryStage, scene);
		} catch (EntityExistException | EmptyFieldsException e) {
			createError(error, e.getMessage());
		}
	}

	public void toRegistrationPage() {
		try {
			registrationControler.loadRegistrationForm(primaryStage, scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
