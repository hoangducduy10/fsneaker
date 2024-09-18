package com.project.fsneaker.services;

import com.project.fsneaker.dtos.CategoryDTO;
import com.project.fsneaker.models.Category;

import java.util.List;

public interface ICatogoryService {

    Category createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

}
