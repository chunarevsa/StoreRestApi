package com.chunarevsa.Website;

import com.chunarevsa.Website.dto.Currency.CurrencyValidator;
import com.chunarevsa.Website.dto.Item.ItemValidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebsiteApplication {

	@Bean
	public ItemValidator itemValidator() {
		return new ItemValidator();
	}

	@Bean
	public CurrencyValidator currencyValidator() {
		return new CurrencyValidator();
	}

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(WebsiteApplication.class, args);

	} 
}
