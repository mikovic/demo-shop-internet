package com.mikovic.demoshopinternet.repositories;


import com.mikovic.demoshopinternet.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findAllByPriceBetween(Pageable pageable, double min, double max);

    Product findOneByTitle(String title);


    @Query(value = "SELECT DISTINCT product FROM Product product left join fetch product.category category left join fetch product.images images WHERE product.category.id =:id",
            countQuery = "select count(product) FROM Product product left join product.category category left join product.images images WHERE product.category.id =:id")
    public Page<Product> findAllByCategoryId (Pageable pageable,@Param("id") Long id);

}
