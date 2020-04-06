package com.example.bsep2020;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Bsep2020Application {

	public static void main(String[] args) {
		SpringApplication.run(Bsep2020Application.class, args);
		System.out.println("radi");
	}

}
