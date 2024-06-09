package com.tennissupplies.tennissuppliesbackend.services;

import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.models.Product;
import com.tennissupplies.tennissuppliesbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(String name, Category category) {
        return productRepository.findByFilters(name, category);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
