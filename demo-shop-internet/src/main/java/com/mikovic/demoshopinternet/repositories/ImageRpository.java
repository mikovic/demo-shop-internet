package com.mikovic.demoshopinternet.repositories;

import com.mikovic.demoshopinternet.entities.Image;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRpository extends PagingAndSortingRepository<Image, Long> {
}
