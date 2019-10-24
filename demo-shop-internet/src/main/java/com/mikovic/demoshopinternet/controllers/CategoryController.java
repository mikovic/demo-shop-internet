package com.mikovic.demoshopinternet.controllers;

import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.services.CategoryService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/categories")
@Transactional
public class CategoryController {

    final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/list")
    @Transactional
    public String showStudentsList(Model model) {
        List<Category> allCategories = categoryService.getCategoryList();
        model.addAttribute("categoryList",allCategories );
        return "categories-list";
    }

    @GetMapping("/create")
    public String createForm(Model theModel) {

        theModel.addAttribute("category", new Category());
        return "category";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/create")
    public String processCreateCategory(@Valid @ModelAttribute("category") Category category,
                                        BindingResult bindingResult, Model uiModel,
                                        HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                                        @RequestParam(value="photo", required=false) MultipartFile file
    ) {

        logger.info("Creating category");
        if (bindingResult.hasErrors()) {

            return "category";
        }
        uiModel.asMap().clear();

        // Process upload file
        if (file != null) {
            logger.info("File name: " + file.getName());
            logger.info("File size: " + file.getSize());
            logger.info("File content type: " + file.getContentType());
            byte[] fileContent = null;
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null) logger.info("File inputstream is null");
                fileContent = IOUtils.toByteArray(inputStream);
                category.setPhoto(fileContent);
            } catch (IOException ex) {
                logger.error("Error saving uploaded file");
            }
            category.setPhoto(fileContent);
        }

        categoryService.save(category);
        return "redirect:/categories/create";
    }
    @RequestMapping(value = "/{id}/photo", method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadImage(@PathVariable("id") Long id) {

        Category category = categoryService.findOneByCategoryId(id);

        if (category.getPhoto() != null) {
            logger.info("Downloading image for id: {} with size: {}", category.getPhoto(), category.getPhoto().length);
        }

        return category.getPhoto();
    }




}
