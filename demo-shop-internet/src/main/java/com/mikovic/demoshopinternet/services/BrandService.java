package com.mikovic.demoshopinternet.services;

import com.mikovic.demoshopinternet.entities.Brand;
import com.mikovic.demoshopinternet.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;
    public List<Brand> findAll(){
        return (List<Brand>) brandRepository.findAll();
    }
}
