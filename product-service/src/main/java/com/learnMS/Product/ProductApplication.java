package com.learnMS.Product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ProductApplication implements CommandLineRunner {

	@Value("${server.port}")
	private String serverPort;

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Product Service Running At {}", serverPort);
	}
}
