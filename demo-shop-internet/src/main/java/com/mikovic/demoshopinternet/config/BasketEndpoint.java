package com.mikovic.demoshopinternet.config;

import com.mikovic.demoshopinternet.gener.GetBasketRequest;
import com.mikovic.demoshopinternet.gener.GetBasketResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class BasketEndpoint {
    private static final String NAMESPACE_URI = "http://www.mikovic.com/demoshopinternet/gener";

    private BasketRepository basketRepository;

    @Autowired
    public BasketEndpoint(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBasketRequest")
    @ResponsePayload
    public GetBasketResponse getGreeting(@RequestPayload GetBasketRequest request) throws DatatypeConfigurationException {
        GetBasketResponse response = new GetBasketResponse();
        response.setBasket(basketRepository.getBasket(request.getOrderId()));

        return response;
    }
}

