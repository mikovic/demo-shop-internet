package com.mikovic.demoshopinternet.controllers;

import com.mikovic.demoshopinternet.bus.RabbitmqDemoApplication;
import com.mikovic.demoshopinternet.services.CategoryService;
import com.mikovic.demoshopinternet.services.SubcategoryService;
import main.java.com.mikovic.cloud.client.Produceur;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@FeignClient("geek-spring-cloud-eureka-client")
public class MainController {
    // https://getbootstrap.com/docs/4.1/getting-started/introduction/csrf
    @Autowired
    CategoryService categoryService;
    @Autowired
    SubcategoryService subcategoryService;
    @Autowired
    Produceur produceur;

    @RequestMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategories", subcategoryService.findall());
        model.addAttribute("message", produceur.getMessage());

        return "index";
    }
}
