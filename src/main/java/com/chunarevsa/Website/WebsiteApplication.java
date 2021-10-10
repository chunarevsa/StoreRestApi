package com.chunarevsa.Website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebsiteApplication {

	/* @Bean
	public ItemService itemValidator() {
		return new ItemService();
	}

	@Bean
	public CurrencyService currencyValidator() {
		return new CurrencyService();
	} */

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(WebsiteApplication.class, args);

	} 
}
