package com.mikovic.demoshopinternet.controllers;
import com.mikovic.demoshopinternet.entities.Category;
import com.mikovic.demoshopinternet.entities.Image;
import com.mikovic.demoshopinternet.entities.Product;
import com.mikovic.demoshopinternet.services.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
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
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    ImageService imageService;
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
        theModel.addAttribute("brands",brandService.findAll());
        theModel.addAttribute("subcategories",subcategoryService.findall());
        return "product-form";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/create")
    public String processCreateProduct(@Valid @ModelAttribute("product") Product product,
                                       BindingResult bindingResult, Model uiModel,
                                       MultipartHttpServletRequest request, RedirectAttributes redirectAttributes,
                                       @RequestParam(value="titles") String[] titles,
                                       @RequestParam(value="files") MultipartFile[] files
    ) {

        logger.info("Creating product");
        if (bindingResult.hasErrors()) {

            return "product-form";
        }
        uiModel.asMap().clear();

        // Process upload file
        int i = 0;
        if (files != null && files.length > 0) {

                for (MultipartFile file : files) {
                    if(!file.isEmpty()) {
                        String pathToImage = imageService.saveFile(file);
                        Image image = new Image();
                        image.setPath(pathToImage);
                        image.setTitle(titles[i]);
                        product.addImage(image);
                        i++;
                    }
                }
        }
        productService.save(product);
        return "redirect:/products/create";
    }
}
