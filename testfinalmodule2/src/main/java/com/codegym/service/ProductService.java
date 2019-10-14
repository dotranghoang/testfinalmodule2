package com.codegym.service;

import com.codegym.model.Product;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);

    void save(Product product);

    void remove(Long e);

    Product findById(Long id);

    Iterable<Product> findAllByCategory(Category category);

    Page<Product> findAllByNameContaining(String name, Pageable pageable);
}

