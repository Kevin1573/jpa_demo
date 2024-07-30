package com.xboot.jpa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

//@Profile("dev")
@SpringBootApplication
public class JpaDemoApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "test");
		SpringApplication.run(JpaDemoApplication.class, args);
	}

}
