package com.sherm.pfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.sherm.pfinance.models")
public class PfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfinanceApplication.class, args);
	}
}
