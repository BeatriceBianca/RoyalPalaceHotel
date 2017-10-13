package com.hotel.royalpalace.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.hotel.royalpalace.model", "com.hotel.royalpalace.main", "controller",
		"com.hotel.royalpalace.service", "com.hotel.royalpalace.repository", "config",
		"com.hotel.royalpalace.auxiliary"})
@EnableJpaRepositories("com.hotel.royalpalace.repository")
@EntityScan(basePackages = {"com.hotel.royalpalace.model"})
@EnableScheduling
public class RoyalpalaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoyalpalaceApplication.class, args);
	}
}
