package com.nexcart.nexcart_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class NexcartBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexcartBackendApplication.class, args);
	}

}
