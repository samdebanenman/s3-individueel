package com.tennissupplies.tennissuppliesbackend.repository;
import com.tennissupplies.tennissuppliesbackend.models.Category;
import com.tennissupplies.tennissuppliesbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE (:name IS NULL OR p.name LIKE %:name%) AND (:category IS NULL OR :category MEMBER OF p.categories)")
    List<Product> findByFilters(@Param("name") String name, @Param("category") Category category);
}
