package com.mikovic.demoshopinternet.config;

import com.mikovic.demoshopinternet.DemoShopInternetApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoShopInternetApplication.class);
	}

}
