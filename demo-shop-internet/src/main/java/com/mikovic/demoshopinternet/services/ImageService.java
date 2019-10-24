package com.mikovic.demoshopinternet.services;

import com.mikovic.demoshopinternet.entities.Image;
import com.mikovic.demoshopinternet.repositories.ImageRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    ImageRpository imageRpository;

    public Image findById (Long id) {

        Optional<Image> image = imageRpository.findById(id);
        if (image.isPresent()) {
            return image.get();
        }
        return null;
    }
}
