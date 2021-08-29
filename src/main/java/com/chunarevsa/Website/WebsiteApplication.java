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

/* Общие вопросы задачи
	- Вынести бины в конфигурацию
*/
/* TSA-2
	-  Как должна происходить ковертация валют? 
*/
/* TSA-9
	- Нужно ли у Price пункт actice?
	- Подключить изменение валют в Price Service b PriceValidator
	- Где должна проходить проверку на незаполенные данные 
	и адекватный формат числа для price?
 */



