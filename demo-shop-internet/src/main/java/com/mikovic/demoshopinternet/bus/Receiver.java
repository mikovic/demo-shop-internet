package com.mikovic.demoshopinternet.bus;


import com.mikovic.demoshopinternet.entities.Order;
import com.mikovic.demoshopinternet.entities.OrderItem;
import com.mikovic.demoshopinternet.services.OrderService;
import com.mikovic.demoshopinternet.utils.ShoppingCart;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    OrderService orderService;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessageFromTopic(Order order) {
        Order saveOrder = orderService.saveOrder(order);
        System.out.println("Стоимость Оформленного заказ равна:");
        System.out.println(saveOrder.getPrice());
        System.out.println("Заказ #" + saveOrder.getId() + "  состоит из:");
        for(OrderItem item: saveOrder.getOrderItems()){
            System.out.println(item.getProduct().getTitle() + ": " + item.getQuantity()+" шт." );
        }
rabbitTemplate.convertAndSend(RabbitmqDemoApplication.topicExchangeName2,"basket", saveOrder.getId());


    }

    public void receiveMessageFromDirect(ShoppingCart cart) {
        System.out.println("Стоимость корзины равна:");
        System.out.println(cart.getTotalCost());
        //System.out.println("Received from direct <" + message + ">");
    }

//    public void receiveMessageFromDirect(String message) {
//        System.out.println("Received from direct <" + message + ">");
//        rabbitTemplate.convertAndSend(RabbitmqDemoApplication.topicExchangeName, "foo.bar.baz", "Confirmed from direct!");
//    }
}
