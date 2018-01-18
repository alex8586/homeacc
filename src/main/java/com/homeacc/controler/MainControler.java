package com.homeacc.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.repository.CategoryRepository;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Component
public class MainControler {

	@FXML
	private TextField txtAddCategory;

	@Autowired
	private CategoryRepository categoryRepository;

	public void addCategory() {
		String txt = txtAddCategory.getText();
		Category category = new Category();
		category.setName(txt);
		categoryRepository.save(category);
	}
}
