package com.homeacc.service;

import java.util.List;

import com.homeacc.entity.Category;

public interface CategoryService {

	void createCategory(String categoryName, long groupId);

	List<Category> getAll();

	Category getByName(String categoryName);

	Category updateCategory(Category category);

	void deleteCategory(Category category);

}
