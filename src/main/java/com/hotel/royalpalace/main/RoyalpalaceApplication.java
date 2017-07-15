package com.hotel.royalpalace.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"configuration", "ro.hotel.royalpalace.model", "ro.hotel.royalpalace.main", "controller", "ro.hotel.royalpalace.service", "ro.hotel.royalpalace.auxiliary"})
@EnableJpaRepositories("ro.hotel.royalpalace.repository")
@EntityScan(basePackages = {"ro.hotel.royalpalace.model"})
@EnableScheduling
public class RoyalpalaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoyalpalaceApplication.class, args);
	}
}
