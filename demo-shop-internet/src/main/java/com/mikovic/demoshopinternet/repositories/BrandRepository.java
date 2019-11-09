package com.mikovic.demoshopinternet.repositories;

import com.mikovic.demoshopinternet.entities.Brand;
import com.mikovic.demoshopinternet.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository  extends CrudRepository<Brand, Long> {
}
