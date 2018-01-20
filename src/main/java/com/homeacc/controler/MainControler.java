package com.homeacc.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.service.CategoryService;
import com.homeacc.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Component
public class MainControler {

	@FXML
	private TextField txtAddCategory;
	@FXML
	private TextField txtAddUser;

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;

	public void addCategory() {
		categoryService.save(txtAddCategory.getText());
	}

	public void addUser() {
		userService.save(txtAddUser.getText());
	}
}
