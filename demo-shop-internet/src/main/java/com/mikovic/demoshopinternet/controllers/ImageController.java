package com.mikovic.demoshopinternet.controllers;

import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.entities.Image;
import com.mikovic.demoshopinternet.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/images")
@Transactional
public class ImageController {

    @Autowired
    ImageService imageService;
    final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @RequestMapping(value = "/{id}/photo", method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadImage(@PathVariable("id") Long id) {

        Image image = imageService.findById(id);

        if (image.getPhoto() != null) {
            logger.info("Downloading image for id: {} with size: {}", image.getPhoto(), image.getPhoto().length);
        }

        return image.getPhoto();
    }
}
