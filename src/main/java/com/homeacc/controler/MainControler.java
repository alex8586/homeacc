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

	@Autowired
	private SpringFXMLLoader springLoader;
	@Autowired
	private ChartControler chartControler;

	public void reloadChart() {
		if (recordsChanged) {
			chartControler.loadBarChart(BudgetTypeEnum.EXPENSES, null, null);
			recordsChanged = false;
		}
	}

	public void loadApplication(Stage primaryStage, Scene scene) throws Exception {
		FXMLLoader loader = springLoader.getLoader(MAIN_PATH);
		Parent root = loader.load();
		scene.setRoot(root);

		primaryStage.setScene(scene);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.show();
	}

}
