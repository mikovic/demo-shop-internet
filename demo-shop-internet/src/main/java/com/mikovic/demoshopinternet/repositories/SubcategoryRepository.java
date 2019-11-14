package com.mikovic.demoshopinternet.repositories;

import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.entities.Subcategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  SubcategoryRepository extends CrudRepository<Subcategory, Long> {
    @Query("SELECT DISTINCT subcat FROM Subcategory  subcat left join fetch subcat.category where subcat.category.id =:categoryId")
    public List<Subcategory> getSubcategoryListByCategoryId (Long categoryId );
}
