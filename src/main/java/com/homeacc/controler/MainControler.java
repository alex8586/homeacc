package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.MAIN_PATH;
import static com.homeacc.appconst.AppFieldsConst.APP_TITLE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.classifier.BudgetTypeEnum;
import com.homeacc.main.SpringFXMLLoader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class MainControler extends ChangeRecordControler {

	private Stage primaryStage;
	private Scene scene;

	@Autowired
	private SpringFXMLLoader springLoader;
	@Autowired
	private ChartControler chartControler;
	@Autowired
	private LoginControler loginControler;
	@Autowired
	private ReportControler reportControler;

	public void reloadData() {
		if (recordsChanged) {
			chartControler.loadBarChart(BudgetTypeEnum.EXPENSES, null, null, 0);
			reportControler.loadReport();
			recordsChanged = false;
		}
		clearError(chartControler.getInfo());
	}

	public void loadApplication(Stage stage, Scene scene, long groupId) throws Exception {
		this.scene = scene;
		this.primaryStage = stage;
		ChangeRecordControler.groupId = groupId;
		FXMLLoader loader = springLoader.getLoader(MAIN_PATH);
		Parent root = loader.load();
		this.scene.setRoot(root);

		primaryStage.setScene(scene);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();
	}

	public void logout() throws Exception {
		loginControler.loadLoginForm(primaryStage, null, scene.getWidth(), scene.getHeight());
	}

}
