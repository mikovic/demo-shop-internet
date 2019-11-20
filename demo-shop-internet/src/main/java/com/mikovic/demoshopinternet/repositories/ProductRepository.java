package com.mikovic.demoshopinternet.repositories;


import com.mikovic.demoshopinternet.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> , JpaSpecificationExecutor<Product> {
    Page<Product> findAllByPriceBetween(Pageable pageable, double min, double max);

    Product findOneByTitle(String title);

    @Query(value = "SELECT DISTINCT product FROM Product product WHERE product.subcategory.id =:id")

    public Page<Product> findAllBySubcategoryId (Pageable pageable,@Param("id") Long id);
}
