package com.homeacc.repository;

import java.util.List;

import com.homeacc.entity.Category;

public interface CategoryRepository {

	List<Category> getAll(long groupId);

	Category getByName(String name);

}
