package com.tennissupplies.tennissuppliesbackend.services;

import com.tennissupplies.tennissuppliesbackend.controller.WebSocketController;
import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceUnitTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private WebSocketController webSocketController;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetCategoryById() {
        Category mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setName("Test Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));

        Category result = categoryService.getCategoryById(1L);

        assertEquals("Test Category", result.getName());
    }

    @Test
    void testGetCategoryById_NotFound() {
        // Arrange: Set up the mock to return an empty Optional
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Expect an EntityNotFoundException to be thrown
        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

        @Test
    void testSaveCategory() {
        Category categoryToSave = new Category();
        categoryToSave.setName("New Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(categoryToSave);

        Category savedCategory = categoryService.saveCategory(categoryToSave);

        assertEquals("New Category", savedCategory.getName());
    }

    @Test
    void testDeleteCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        // Act: Call deleteCategory method on the service
        categoryService.deleteCategory(1L);

        // Assert: Verify that deleteById was called on the repository with the correct ID
        verify(categoryRepository).deleteById(1L);

        // Verify: Ensure webSocketController.notifyProductChange() was called once
    }

    @Test
    void testGetAllCategories() {
        Category mockCategory1 = new Category();
        mockCategory1.setId(1L);
        mockCategory1.setName("Category 1");

        Category mockCategory2 = new Category();
        mockCategory2.setId(2L);
        mockCategory2.setName("Category 2");

        when(categoryRepository.findAll()).thenReturn(List.of(mockCategory1, mockCategory2));

        assertEquals(2, categoryService.getAllCategories().size());
    }

    @Test
    void testSaveCategory_NullCategory() {

        assertThrows(IllegalArgumentException.class, () -> categoryService.saveCategory(null));
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void testDeleteCategory_NonExistingId() {
        doNothing().when(categoryRepository).deleteById(anyLong());

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}
