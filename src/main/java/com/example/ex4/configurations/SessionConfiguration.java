package com.example.ex4.configurations;

import com.example.ex4.session.CartSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

/**
 * Configuration class for session-scoped beans.
 * <p>
 * Defines beans with HTTP session lifecycle, such as a shopping cart session.
 * </p>
 */
@Configuration
public class SessionConfiguration {

    /**
     * Provides a session-scoped CartSession bean.
     * <p>
     * A new CartSession instance is created for each user session to manage cart items.
     * </p>
     *
     * @return a CartSession tied to the current HTTP session
     */
    @Bean
    @SessionScope
    public CartSession cartSessionBean() {
        return new CartSession();
    }
}
