package com.arpatilmh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.arpatilmh.repository")
@EntityScan("com.arpatilmh.repository")
public class UrlShortnerApplication {
    private static final Logger logger = LoggerFactory.getLogger(UrlShortnerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(UrlShortnerApplication.class, args);
        logger.info("Hello, I am URL shortner service");
    }
}
