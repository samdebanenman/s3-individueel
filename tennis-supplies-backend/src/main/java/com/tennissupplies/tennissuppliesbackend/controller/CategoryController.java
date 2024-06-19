package com.tennissupplies.tennissuppliesbackend.controller;

import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try{

            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        }
        catch(EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleEntityNotFoundException(EntityNotFoundException ex) {
        // Log the exception or do something else, if needed
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException(IllegalArgumentException ex) {
        // Log the exception or do something else, if needed
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
        // Log the exception or do something else, if needed
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try{
            System.out.println("save category object CONTROLLER: " + (category==null?"null":category.getName()));
            if(category!=null) System.out.println("########## CAT SRV "+categoryService+"="+categoryService.getClass());
            Category savedCategory = categoryService.saveCategory(category);
            System.out.println("save category object CONTROLLER: " + (savedCategory==null?"null":savedCategory.getName()));

            return ResponseEntity.ok(savedCategory);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        try {
            Category existingCategory = categoryService.getCategoryById(id);
            if (existingCategory != null) {
                existingCategory.setName(categoryDetails.getName());
                Category updatedCategory = categoryService.saveCategory(existingCategory);
                return ResponseEntity.ok(updatedCategory);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
