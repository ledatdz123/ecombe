package com.example.ecombe.repository;

import com.example.ecombe.entities.Category;
import com.example.ecombe.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
