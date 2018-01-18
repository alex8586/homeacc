package com.homeacc.main;

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

		ctx.getBean(SpringFXMLLoader.class);

		FXMLLoader loader = ctx.getBean(SpringFXMLLoader.class).getLoader("/fxml/main.fxml");
		Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Home bookkeeping");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

