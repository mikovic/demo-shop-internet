package com.mikovic.demoshopinternet.config;

import com.mikovic.demoshopinternet.entities.Order;
import com.mikovic.demoshopinternet.entities.OrderItem;
import com.mikovic.demoshopinternet.gener.Basket;
import com.mikovic.demoshopinternet.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.List;

@Component
public class BasketRepository {
    @Autowired
    private OrderService orderService;
    public Basket getBasket(Long orderId) throws DatatypeConfigurationException {
        Assert.notNull(orderId, "Имя не может быть пустым");
        Order order = orderService.findById(orderId);
        Basket basket = new Basket();
        List<String> titles = null;
        for(OrderItem item: order.getOrderItems()){
            titles.add(item.getProduct().getTitle());

        }
        basket.setTitles(titles.toString());
        basket.setPrice(order.getPrice().toString());
        basket.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(order.getCreateAt().toString()));
        return basket;
    }


}


