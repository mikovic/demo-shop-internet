package com.mikovic.demoshopinternet.services;


import com.mikovic.demoshopinternet.entities.Product;
import com.mikovic.demoshopinternet.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
    public void save(Product product) {
        productRepository.save(product);
    }
}
