package com.xantrix.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients()
public class PromoWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PromoWebServiceApplication.class, args);
	}

}