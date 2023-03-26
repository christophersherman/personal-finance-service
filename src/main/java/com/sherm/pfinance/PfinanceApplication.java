package com.sherm.pfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("com.sherm.pfinance.models")
@EnableScheduling
public class PfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfinanceApplication.class, args);
	}
}
