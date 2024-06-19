package com.tennissupplies.tennissuppliesbackend.services;

import com.tennissupplies.tennissuppliesbackend.controller.WebSocketController;
import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final WebSocketController webSocketController;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    public Category saveCategory(Category savcategory) {
        System.out.println("save category object: " + (savcategory==null?"null":savcategory.getName()));
        if (savcategory == null || savcategory.getName() == null || savcategory.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        Category cat = categoryRepository.save(savcategory);
        webSocketController.notifyProductChange();
        return cat;
    }

    public void deleteCategory(Long id) {
        System.out.println("delete category id: " + id);
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
        webSocketController.notifyProductChange();

    }

    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }
}
