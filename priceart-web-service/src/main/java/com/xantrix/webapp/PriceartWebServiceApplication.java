package com.xantrix.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.xantrix.webapp.controller")
public class PriceartWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceartWebServiceApplication.class, args);
	}

}
