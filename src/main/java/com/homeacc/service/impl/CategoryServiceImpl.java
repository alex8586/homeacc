package com.homeacc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeacc.entity.Category;
import com.homeacc.entity.Groups;
import com.homeacc.exception.ValidationException;
import com.homeacc.repository.BudgetRecordsRepository;
import com.homeacc.repository.CategoryRepository;
import com.homeacc.repository.GenericRepository;
import com.homeacc.service.CategoryService;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BudgetRecordsRepository budgetRecordsRepository;
	@Autowired
	private GenericRepository genericRepository;

	@Override
	public void createCategory(String categoryName, long groupId) {
		Category category = categoryRepository.getByName(categoryName);
		if (category != null) {
			throw new ValidationException("Category with this name already exist !");
		}
		category = new Category();
		category.setName(categoryName);
		Groups group = genericRepository.getById(Groups.class, groupId);
		category.setGroups(group);
		genericRepository.save(category);
	}

	@Override
	public Category updateCategory(Category category) {
		genericRepository.update(category);
		return categoryRepository.getByName(category.getName());
	}

	@Override
	public void deleteCategory(Category category) {
		budgetRecordsRepository.deleteWithCategory(category.getId());
		genericRepository.delete(category);
	}

	@Override
	public Category getByName(String categoryName) {
		return categoryRepository.getByName(categoryName);
	}

	@Override
	public List<Category> getAll(long groupId) {
		return categoryRepository.getAll(groupId);
	}

}
