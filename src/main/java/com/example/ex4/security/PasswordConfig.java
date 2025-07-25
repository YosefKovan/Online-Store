package com.example.ex4.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding strategy.
 * <p>
 * Provides a BCrypt-based PasswordEncoder bean to be used
 * by Spring Security for hashing user passwords.
 */
@Configuration
public class PasswordConfig {

    /**
     * Creates and exposes a BCryptPasswordEncoder bean.
     * <p>
     * This encoder applies the BCrypt hashing algorithm with a random
     * salt and adjustable strength factor.
     *
     * @return a PasswordEncoder implementation using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
