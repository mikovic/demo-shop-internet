package com.mikovic.demoshopinternet.controllers;
import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.entities.Image;
import com.mikovic.demoshopinternet.entities.Product;
import com.mikovic.demoshopinternet.services.CategoryService;
import com.mikovic.demoshopinternet.services.ProductService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/products")
@Transactional
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @Autowired
    private CategoryService categoryService;
    final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping("/list")
    @Transactional
    public String showStudentsList(Model model) {
        List<Product> allProduct = productService.getAllProducts();
        model.addAttribute("productsList", allProduct);
        return "products-list";
    }

    @GetMapping("/create")
    public String createForm(Model theModel) {

        theModel.addAttribute("product", new Product());
        theModel.addAttribute("categories",categoryService.findAll());
        return "product-form";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/create")
    public String processCreateProduct(@Valid @ModelAttribute("product") Product product,
                                        BindingResult bindingResult, Model uiModel,
                                        HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                                        @RequestParam(value="images") MultipartFile[] uploadedImages
    ) {

        logger.info("Creating product");
        if (bindingResult.hasErrors()) {

            return "product-form";
        }
        uiModel.asMap().clear();

        // Process upload file
        if (uploadedImages != null && uploadedImages.length > 0) {

                for (MultipartFile file : uploadedImages) {
                    byte[] fileContent = null;
                    Image image = new Image();
                    try {
                        InputStream inputStream = file.getInputStream();
                        if (inputStream == null) logger.info("File inputstream is null");
                        fileContent = IOUtils.toByteArray(inputStream);
                        image.setPhoto(fileContent);
                        product.addImage(image);
                    } catch (IOException ex) {
                        logger.error("Error saving uploaded file");
                    }
                    image.setPhoto(fileContent);

                }


        }
        productService.save(product);
        return "redirect:/categories/create";
    }
}
