package com.mikovic.demoshopinternet.services;

import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getCategoryList () {
       return categoryRepository.getCategoryList();
    }
    public Category findOneByCategoryId (Long id) {
        return categoryRepository.findOneByCategoryId(id);
    }
    public void save(Category category){
        categoryRepository.save(category);

    }
    public List<Category> findAll () {
        return (List<Category>) categoryRepository.findAll();
    }

}
