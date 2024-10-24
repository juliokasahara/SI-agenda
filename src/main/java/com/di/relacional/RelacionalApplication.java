package com.di.relacional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RelacionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelacionalApplication.class, args);
	}

}
