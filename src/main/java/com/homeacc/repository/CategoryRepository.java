package com.homeacc.repository;

import java.util.List;

import com.homeacc.entity.Category;

public interface CategoryRepository {

	void save(Category category);

	List<Category> getAll();

	Category getByName(String name);

	void update(Category category);
}
