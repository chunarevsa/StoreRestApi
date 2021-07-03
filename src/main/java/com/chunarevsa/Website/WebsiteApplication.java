package com.chunarevsa.Website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
		/* Если при запуске ругается на порт 8080, то нужно зайти в
		resources/application.properties 
		Если в последней строке выводится Started - значит всё ок
		Нужно обратить внимание на Tomcat - позволяет просматривать веб сайт */
		
	} 

}
