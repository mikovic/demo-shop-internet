package com.mikovic.demoshopinternet.bus;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqDemoApplication {
    //это название маршрутизаторов, они распределяют сообщения по названиям
    public static final String topicExchangeName = "spring-boot-exchange-topic";
    public static final String topicExchangeName2 = "spring-boot-exchange-topiс2";
    public static final String directExchangeName = "spring-boot-exchange-direct";
    //очередь это как почтовый ящик.Она классифицируется по теме
    public static final String queueTopicName = "spring-boot-topic-queue";
    public static final String queueTopicName2 = "spring-boot-topic-queue2";
    //здесь просто очередь
    public static final String queueDirectName = "spring-boot-direct-queue";

    @Bean
    Queue queueTopic() {
        return new Queue(queueTopicName, false);
    }
    @Bean
    Queue queueTopic2() {
        return new Queue(queueTopicName2, false);
    }

    @Bean
    Queue queueDirect() {
        return new Queue(queueDirectName, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }
    @Bean
    TopicExchange topicExchange2() {
        return new TopicExchange(topicExchangeName2);
    }


    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }
//Привязка маршрутизаторов (exchange) c очередью по ключам  "Topic Process" и   Direct Process
    @Bean
    Binding bindingTopic(@Qualifier("queueTopic") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("order");
    }
    @Bean
    Binding bindingTopic2(@Qualifier("queueTopic2") Queue queue2, TopicExchange topicExchange2) {
        return BindingBuilder.bind(queue2).to(topicExchange2).with("basket");
    }

    @Bean
    Binding bindingDirect(@Qualifier("queueDirect") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("Direct Process");
    }
//устанавливаем слушателей на тему и на очередь
    @Bean
    SimpleMessageListenerContainer containerForTopic(ConnectionFactory connectionFactory, @Qualifier("listenerAdapterForTopic") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueTopicName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerForDirect(ConnectionFactory connectionFactory, @Qualifier("listenerAdapterForDirect") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueDirectName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterForTopic(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageFromTopic");
    }

    @Bean
    MessageListenerAdapter listenerAdapterForDirect(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageFromDirect");
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApplication.class, args);
    }
}
