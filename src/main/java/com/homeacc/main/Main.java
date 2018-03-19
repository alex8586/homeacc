package com.homeacc.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.homeacc.controler.LoginControler;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
        launch(args);
    }

	@Override
    public void start(Stage primaryStage) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");

		LoginControler controler = ctx.getBean(LoginControler.class);
		controler.loadLoginForm(primaryStage, null, null, null);
    }
}

