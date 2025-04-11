package com.ramo.iFootballgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient

public class IFootballgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IFootballgatewayApplication.class, args);
	}

}
