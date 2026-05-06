package com.poc.collab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CollabDocsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollabDocsApplication.class, args);
    }
}
