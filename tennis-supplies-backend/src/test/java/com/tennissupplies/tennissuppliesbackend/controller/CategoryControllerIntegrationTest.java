package com.tennissupplies.tennissuppliesbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        // Clean up before each test
        categoryService.deleteAllCategories();
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        categoryService.deleteAllCategories();
    }

    @Test
    void testGetAllCategories() throws Exception {
        Category category1 = new Category();
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setName("Category 2");

        categoryService.saveCategory(category1);
        categoryService.saveCategory(category2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }

    @Test
    void testGetCategoryById() throws Exception {
        Category category = new Category();
        category.setName("Test Category");

        categoryService.saveCategory(category);
        Long categoryId = category.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"));
    }

    @Test
    void testGetCategoryById_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCategory() throws Exception {
        Category categoryToCreate = new Category();
        categoryToCreate.setName("New Category");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryToCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void testCreateCategory_InvalidInput() throws Exception {
        Category categoryToCreate = new Category(); // Missing name

        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryToCreate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateCategory() throws Exception {
        Category existingCategory = new Category();
        existingCategory.setName("Existing Category");

        categoryService.saveCategory(existingCategory);
        Long categoryId = existingCategory.getId();

        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void testUpdateCategory_NotFound() throws Exception {
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategory)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategory() throws Exception {
        Category category = new Category();
        category.setName("Category to delete");

        categoryService.saveCategory(category);
        Long categoryId = category.getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/" + categoryId))
                .andExpect(status().isOk());

        // Verify the category was deleted
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/" + categoryId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCategory_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/100"))
                .andExpect(status().isNotFound());
    }
}
