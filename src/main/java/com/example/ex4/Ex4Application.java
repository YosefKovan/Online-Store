package com.example.ex4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Ex4 Spring Boot application.
 * <p>
 * This class bootstraps the Spring application context
 * and starts the embedded server.
 * </p>
 */
@SpringBootApplication
public class Ex4Application {

    /**
     * Application entry point that launches the Spring Boot application.
     *
     * @param args runtime arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Ex4Application.class, args);
    }

}
