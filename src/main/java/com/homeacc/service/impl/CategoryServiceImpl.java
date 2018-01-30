package com.homeacc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.exception.EntityExistException;
import com.homeacc.repository.CategoryRepository;
import com.homeacc.service.CategoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void createCategory(String categoryName) {
		Category category = categoryRepository.getByName(categoryName);
		if (category != null) {
			throw new EntityExistException("Category with this name already exist !");
		}
		category = new Category();
		category.setName(categoryName);
		categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(Category category) {
		categoryRepository.update(category);
		return categoryRepository.getByName(category.getName());
	}

	@Override
	public Category getByName(String categoryName) {
		return categoryRepository.getByName(categoryName);
	}

	@Override
	public ObservableList<Category> getAll() {
		ObservableList<Category> categories = FXCollections.observableArrayList();
		categories.addAll(categoryRepository.getAll());
		return categories;
	}

}
