package com.learnMS.discoveryserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication implements CommandLineRunner {

    @Autowired
    private Environment environment;

    private Logger logger = LoggerFactory.getLogger(DiscoveryServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Discovery Server Running At {}", environment.getProperty("server.port"));
    }
}
