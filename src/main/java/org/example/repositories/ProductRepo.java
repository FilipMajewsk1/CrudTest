package org.example.repositories;

import org.example.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepo {
    Product save(Product product);
    Optional<Product> findById(int id);
    List<Product> findAll();
    void deleteById(int id);
}
