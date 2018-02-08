package com.homeacc.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.homeacc.appconst.AppConst;

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

		FXMLLoader loader = ctx.getBean(SpringFXMLLoader.class).getLoader("/fxml/main.fxml");
		Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Home bookkeeping");
        primaryStage.setMinWidth(AppConst.APP_WIDTH);
        primaryStage.setMinHeight(AppConst.APP_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

