package com.ramo.iFootballFixtures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class IFootballFixturesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IFootballFixturesApplication.class, args);
	}

}
