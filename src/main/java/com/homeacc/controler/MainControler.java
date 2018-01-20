package com.homeacc.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.entity.Users;
import com.homeacc.repository.CategoryRepository;
import com.homeacc.repository.UsersRepository;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Component
public class MainControler {

	@FXML
	private TextField txtAddCategory;
	@FXML
	private TextField txtAddUser;

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UsersRepository usersRepository;

	public void addCategory() {
		String txt = txtAddCategory.getText();
		Category category = new Category();
		category.setName(txt);
		categoryRepository.save(category);
	}

	public void addUser() {
		String txt = txtAddUser.getText();
		Users user= new Users();
		user.setName(txt);
		usersRepository.save(user);
	}
}
