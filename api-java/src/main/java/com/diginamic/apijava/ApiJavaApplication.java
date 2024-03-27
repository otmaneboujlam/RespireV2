package com.diginamic.apijava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiJavaApplication.class, args);
	}

}
