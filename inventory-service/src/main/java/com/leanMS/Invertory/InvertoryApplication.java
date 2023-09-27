package com.leanMS.Invertory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvertoryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(InvertoryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Server Started at 8003");
	}
}
