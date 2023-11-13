package org.example.services;

import org.example.models.Client;
import org.example.models.Product;
import org.example.repositories.ProductRepo;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepo productRepository;

    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    public Product placeProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getClient(int id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
