package com.mikovic.demoshopinternet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class DemoShopInternetApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoShopInternetApplication.class, args);
	}

}
