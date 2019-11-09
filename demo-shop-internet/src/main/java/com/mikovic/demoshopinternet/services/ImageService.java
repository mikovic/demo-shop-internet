package com.mikovic.demoshopinternet.services;

import com.mikovic.demoshopinternet.entities.Image;
import com.mikovic.demoshopinternet.repositories.ImageRpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
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
    private static final String UPLOADED_FOLDER = "demo-shop-internet/images/";

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "";
        }
        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        try {
            Path path = Paths.get(UPLOADED_FOLDER + fileName);

            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
