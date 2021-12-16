package com.chunarevsa.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
	WebsiteApplication.class,
	Jsr310JpaConverters.class
})
public class WebsiteApplication {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(WebsiteApplication.class, args);

	} 
}
