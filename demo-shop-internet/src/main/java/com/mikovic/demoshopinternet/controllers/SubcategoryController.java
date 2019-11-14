package com.mikovic.demoshopinternet.controllers;

import com.mikovic.demoshopinternet.entities.Subcategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
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
@RequestMapping("/subcategories")
@Transactional
public class SubcategoryController {

    final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImageService imageService;
    @Autowired
    SubcategoryService subcategoryService;

    @RequestMapping("/list")
    @Transactional
    public String showSubcategoriesList(Model model) {
        List<Subcategory> allSubcategories = subcategoryService.findall();
        model.addAttribute("categoryList",allSubcategories );
        return "subcategories-list";
    }


    @RequestMapping(path = "/{categoryId}", method = RequestMethod.GET)
    public String showSubcategoriesListByCategoryId(@PathVariable(value = "categoryId") Long categoryId, Model model) {
        List<Subcategory> subcategories = subcategoryService.getSubcategoryListByCategoryId(categoryId);
        model.addAttribute("subcategories",subcategories );
        model.addAttribute("categories", categoryService.findAll());
        return "subcategories";
    }

    @GetMapping("/create")
    public String createForm(Model theModel) {

        theModel.addAttribute("subcategory", new Subcategory());
        theModel.addAttribute("categories", categoryService.getCategoryList());
        return "subcategory-form";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/create")
    public String processCreateCategory(@Valid @ModelAttribute("subcategory") Subcategory subcategory,
                                        BindingResult bindingResult, Model uiModel,
                                        HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                                        @RequestParam(value="file", required=false) MultipartFile file
    ) {

        logger.info("Creating subcategory");
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("categories", categoryService.getCategoryList());
            return "subcategory-form";
        }
        uiModel.asMap().clear();

        // Process upload file
        if (file != null) {
            String pathToImage = imageService.saveFile(file);
            Image image = new Image();
            image.setPath(pathToImage);
            subcategory.setImage(image);
            subcategoryService.save(subcategory);
        }
        return "redirect:/subcategories/create";
    }

}
