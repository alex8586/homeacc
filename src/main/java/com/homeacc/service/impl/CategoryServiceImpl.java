package com.homeacc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.repository.CategoryRepository;
import com.homeacc.service.CategoryService;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void save(String categoryName) {
		Category category = new Category();
		category.setName(categoryName);
		categoryRepository.save(category);
	}

}
