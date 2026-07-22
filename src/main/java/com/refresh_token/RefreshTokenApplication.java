package com.refresh_token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RefreshTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefreshTokenApplication.class, args);
	}

}
