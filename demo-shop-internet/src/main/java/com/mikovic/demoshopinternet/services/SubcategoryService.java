package com.mikovic.demoshopinternet.services;

import com.mikovic.demoshopinternet.entities.Subcategory;
import com.mikovic.demoshopinternet.repositories.CategoryRepository;
import com.mikovic.demoshopinternet.repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {
    @Autowired
    SubcategoryRepository subcategoryRepository;
    public List<Subcategory> findall(){
        return (List<Subcategory>) subcategoryRepository.findAll();
    }
    public void save(Subcategory subcategory){
        subcategoryRepository.save(subcategory);
    }
    public List<Subcategory> getSubcategoryListByCategoryId (Long categoryId ){
        return subcategoryRepository.getSubcategoryListByCategoryId(categoryId);
    }
}
