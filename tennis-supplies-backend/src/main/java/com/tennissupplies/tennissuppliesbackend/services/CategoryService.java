package com.tennissupplies.tennissuppliesbackend.services;

import com.tennissupplies.tennissuppliesbackend.controller.WebSocketController;
import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.repository.CategoryRepository;
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
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        Category cat = categoryRepository.save(category);
        webSocketController.notifyProductChange();
        return cat;
    }

    public void deleteCategory(Long id) {
        webSocketController.notifyProductChange();
        categoryRepository.deleteById(id);

    }
}
