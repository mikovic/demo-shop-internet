package com.mikovic.demoshopinternet.repositories;

import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository <Category, Long> {


    @Query("SELECT DISTINCT cat FROM Category cat left join fetch cat.products p GROUP BY cat.id")
    public List<Category> getCategoryList ();

    @Query("SELECT DISTINCT cat FROM Category cat left join fetch cat.products p WHERE cat.id =:id")
    public Category findOneByCategoryId (@Param("id") Long id);



}
