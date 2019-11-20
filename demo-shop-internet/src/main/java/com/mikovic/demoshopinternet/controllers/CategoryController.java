package com.mikovic.demoshopinternet.controllers;

import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.entities.Image;
import com.mikovic.demoshopinternet.services.CategoryService;
import com.mikovic.demoshopinternet.services.ImageService;
import com.mikovic.demoshopinternet.services.SubcategoryService;
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
    @Autowired
    ImageService imageService;
    @Autowired
    SubcategoryService subcategoryService;

    @RequestMapping("/list")
    @Transactional
    public String showCategoriesList(Model model) {
        List<Category> allCategories = categoryService.getCategoryList();
        model.addAttribute("categoryList",allCategories );
        return "categories-list";
    }

    @GetMapping("/create")
    public String createForm(Model theModel) {

        theModel.addAttribute("category", new Category());
        theModel.addAttribute("allSubcategories", subcategoryService.findall());
        return "category";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/create")
    public String processCreateCategory(@Valid @ModelAttribute("category") Category category,
                                        BindingResult bindingResult, Model model,
                                        HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                                        @RequestParam(value="file", required=false) MultipartFile file
    ) {

        logger.info("Creating category");
        if (bindingResult.hasErrors()) {

            return "category";
        }
        model.asMap().clear();

        // Process upload file
        if (file != null) {
            String pathToImage = imageService.saveFile(file);
            Image image = new Image();
            image.setPath(pathToImage);
            category.setImage(image);
            categoryService.save(category);
        }
        model.addAttribute("categories", categoryService.findAll());
        return "redirect:/categories/create";
    }

}
