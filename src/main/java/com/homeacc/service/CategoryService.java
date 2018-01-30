package com.homeacc.service;

import com.homeacc.entity.Category;

import javafx.collections.ObservableList;

public interface CategoryService {

	void createCategory(String categoryName);

	ObservableList<Category> getAll();

	Category getByName(String categoryName);

	Category updateCategory(Category category);

}
