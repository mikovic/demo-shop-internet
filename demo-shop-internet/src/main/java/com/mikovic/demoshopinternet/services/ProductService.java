package com.mikovic.demoshopinternet.services;


import com.mikovic.demoshopinternet.entities.Product;
import com.mikovic.demoshopinternet.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
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
    public Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize, Specification<Product> productSpecification) {
        return productRepository.findAll(productSpecification, PageRequest.of(pageNumber, pageSize));
    }

    public Page<Product> findAllBySubcategoryId (int pageNumber, int pageSize, @Param("subcategoryId") Long subcategoryId){
        return productRepository.findAllBySubcategoryId(PageRequest.of(pageNumber, pageSize), subcategoryId);
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
