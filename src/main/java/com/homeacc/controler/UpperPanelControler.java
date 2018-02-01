package com.homeacc.controler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Component
public class UpperPanelControler {

	@FXML
	private Button bntCategories;

	@Autowired
	private ManageCategoryControler manageCategoryControler;

	public void manageCategory() {
		try {
			manageCategoryControler.openModal(bntCategories.getScene().getWindow());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
