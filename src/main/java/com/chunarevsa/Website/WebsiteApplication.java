package com.chunarevsa.Website;

import com.chunarevsa.Website.repo.ItemRepository;
import com.chunarevsa.Website.service.valid.ItemValid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class WebsiteApplication {

	/*
	@Bean
	public CurrencyService currencyValidator() {
		return new CurrencyService();
	} */

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(WebsiteApplication.class, args);

	} 
}
