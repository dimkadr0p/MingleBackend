package ru.khachidze.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MingleServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MingleServiceApplication.class, args);
	}

}
