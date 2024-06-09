package com.tennissupplies.tennissuppliesbackend.services;

import com.tennissupplies.tennissuppliesbackend.controller.WebSocketController;
import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.models.Product;
import com.tennissupplies.tennissuppliesbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final WebSocketController webSocketController;

    @Autowired
    public ProductService(ProductRepository productRepository, WebSocketController webSocketController) {
        this.productRepository = productRepository;
        this.webSocketController = webSocketController;
    }

    public List<Product> getAllProducts(String name, Category category) {
        return productRepository.findByFilters(name, category);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        Product prod = productRepository.save(product);
        webSocketController.notifyProductChange();
        return prod;
    }

    public void deleteProduct(Long id) {
        webSocketController.notifyProductChange();
        productRepository.deleteById(id);
    }
}
