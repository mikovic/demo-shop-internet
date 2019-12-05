package com.mikovic.demoshopinternet.controllers;

import com.mikovic.demoshopinternet.bus.RabbitmqDemoApplication;
import com.mikovic.demoshopinternet.entities.DeliveryAddress;
import com.mikovic.demoshopinternet.entities.Order;
import com.mikovic.demoshopinternet.entities.Product;
import com.mikovic.demoshopinternet.entities.User;
import com.mikovic.demoshopinternet.repositories.specifications.ProductSpecs;
import com.mikovic.demoshopinternet.services.*;
import com.mikovic.demoshopinternet.utils.Message;
import com.mikovic.demoshopinternet.utils.ShoppingCart;
import org.aspectj.weaver.ast.Or;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private static final int INITIAL_PAGE = 0;
    private static final int PAGE_SIZE = 4;
@Autowired
private ProductService productService;
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private DeliveryAddressService deliverAddressService;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @RequestMapping(path = "/{subcategoryId}", method = RequestMethod.GET)
    public String shopPage(@PathVariable(value = "subcategoryId") Long subcategoryId, Model model,
                           @RequestParam(value = "page") Optional<Integer> page,
                           @RequestParam(value = "word", required = false) String word,
                           @RequestParam(value = "min", required = false) Double min,
                           @RequestParam(value = "max", required = false) Double max
    ) {
        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Specification<Product> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
//        spec.and(ProductSpecs.subcategoryIdEq(subcategoryId));

        if (word != null) {
            spec = spec.and(ProductSpecs.titleContains(word));
            filters.append("&word=" + word);
        }
        if (min != null) {
            spec = spec.and(ProductSpecs.priceGreaterThanOrEq(min));
            filters.append("&min=" + min);
        }
        if (max != null) {
            spec = spec.and(ProductSpecs.priceLesserThanOrEq(max));
            filters.append("&max=" + max);
        }
//        Page<Product> products = productService.getProductsWithPagingAndFiltering(currentPage, PAGE_SIZE, spec);
        Page<Product> products = productService.findAllBySubcategoryId(currentPage,PAGE_SIZE,subcategoryId);

        model.addAttribute("products", products.getContent());
        model.addAttribute("subcategories", subcategoryService.findall());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("subcategoryId", subcategoryId);
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());

        model.addAttribute("filters", filters.toString());

        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("word", word);
        return "shop-page";
    }
    @GetMapping("/product/{id}")
    public String showProduct(Model model, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        Product product = productService.getProductById(id);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", product);
        return "product-page";
    }


    @GetMapping("/cart/add/{id}")
    public String addProductToCart(Model model, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        shoppingCartService.addToCart(httpServletRequest.getSession(), id);
        ShoppingCart cart = shoppingCartService.getCurrentCart(httpServletRequest.getSession());
        rabbitTemplate.convertAndSend(RabbitmqDemoApplication.directExchangeName,"Direct Process", cart);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @GetMapping("/order/fill")
    public String orderFill(Model model, HttpServletRequest httpServletRequest, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        Order order = orderService.makeOrder(shoppingCartService.getCurrentCart(httpServletRequest.getSession()), user);
        List<DeliveryAddress> deliveryAddresses = deliverAddressService.getUserAddresses(user.getId());
        model.addAttribute("order", order);
        model.addAttribute("deliveryAddresses", deliveryAddresses);
        return "order-filler";
    }

    @PostMapping("/order/confirm")
    public String orderConfirm(Model model, HttpServletRequest httpServletRequest, @ModelAttribute(name = "order") Order orderFromFrontend, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        Order order = orderService.makeOrder(shoppingCartService.getCurrentCart(httpServletRequest.getSession()), user);
        if(order.getPrice()==0) {
            model.addAttribute("message", new Message("error", "Корзина пуста"));
            model.addAttribute("order", order);
            List<DeliveryAddress> deliveryAddresses = deliverAddressService.getUserAddresses(user.getId());
            model.addAttribute("deliveryAddresses", deliveryAddresses);
            return "order-filler";
        }
        order.setDeliveryAddress(orderFromFrontend.getDeliveryAddress());
        order.setPhoneNumber(orderFromFrontend.getPhoneNumber());
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));
        order.setDeliveryPrice(0.0);
        rabbitTemplate.convertAndSend(RabbitmqDemoApplication.topicExchangeName,"order", order);

        long id = (long) rabbitTemplate.receiveAndConvert("spring-boot-topic-queue2");
        Order orderFrom = orderService.findById(id);
        model.addAttribute("order", orderFrom );
        return "redirect:/shop/order/"+orderFrom.getId();
    }
    @GetMapping("/order/{id}")
    public String orderPay(Model model, @PathVariable(name = "id") Long id, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order-confirmed";
    }


    @GetMapping("/order/result/{id}")
    public String orderConfirm(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        // todo ждем до оплаты, проверка безопасности и проблема с повторной отправкой письма сделать одноразовый вход
        User user = userService.findByUserName(principal.getName());
        Order confirmedOrder = orderService.findById(id);
        if (!user.getId().equals(confirmedOrder.getUser().getId())) {
            return "redirect:/";
        }
        mailService.sendOrderMail(confirmedOrder);
        model.addAttribute("order", confirmedOrder);
        return "order-result";
    }
}
