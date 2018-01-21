package com.homeacc.service;

import com.homeacc.entity.Category;

import javafx.collections.ObservableList;

public interface CategoryService {

	void save(String categoryName);

	ObservableList<Category> getAll();

}
