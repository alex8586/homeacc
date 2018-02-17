package com.homeacc.main;

import static com.homeacc.appconst.AppConst.APP_HEIGHT;
import static com.homeacc.appconst.AppConst.APP_WIDTH;
import static com.homeacc.appconst.AppConst.MAIN_PATH;
import static com.homeacc.appconst.AppFieldsConst.APP_TITLE;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");

		FXMLLoader loader = ctx.getBean(SpringFXMLLoader.class).getLoader(MAIN_PATH);
		Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setMinWidth(APP_WIDTH);
        primaryStage.setMinHeight(APP_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

