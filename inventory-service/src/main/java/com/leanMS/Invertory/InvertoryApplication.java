package com.leanMS.Invertory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class InvertoryApplication implements CommandLineRunner {

	@Value("${server.port}")
	private String serverPort;

	public static void main(String[] args) {
		SpringApplication.run(InvertoryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Inventory Service Started At {}", serverPort);
	}
}
