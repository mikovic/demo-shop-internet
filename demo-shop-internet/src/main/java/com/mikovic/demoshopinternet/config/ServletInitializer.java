package com.mikovic.demoshopinternet.config;

import com.mikovic.demoshopinternet.DemoShopInternetApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;

public class ServletInitializer extends SpringBootServletInitializer {
	private int maxUploadSizeInMb = 5 * 1024 * 1024;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(DemoShopInternetApplication.class);
	}
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}


	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(maxUploadSizeInMb));
		factory.setMaxRequestSize(DataSize.ofBytes(maxUploadSizeInMb));
		return factory.createMultipartConfig();
	}


}
