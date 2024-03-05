package com.example.appscheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:security.properties")
@SpringBootApplication
public class AppscheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppscheckApplication.class, args);
	}

}
