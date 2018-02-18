package com.homeacc.controler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Component
public class LeftPanelControler {

	@FXML
	private Button bntCategories;
	@FXML
	private Button bntUsers;

	@Autowired
	private ManageCategoryControler manageCategoryControler;
	@Autowired
	private ManageUserControler manageUserControler;

	public void manageCategory() {
		try {
			manageCategoryControler.openModal(bntCategories.getScene().getWindow());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void manageUser() {
		try {
			manageUserControler.openModal(bntUsers.getScene().getWindow());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
