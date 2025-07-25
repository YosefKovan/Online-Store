package com.example.ex4.configurations;

import com.example.ex4.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class responsible for setting up the application's security filters.
 * <p>
 * Defines CORS and CSRF settings, URL authorization rules, login/logout behaviors,
 * and integrates a custom UserDetailsService for authentication.
 * </p>
 */
@Configuration
public class ApplicationConfiguration {

    /**
     * Service providing custom user details for authentication.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Defines the security filter chain for HTTP requests.
     * <p>
     * Configures CORS, CSRF exclusions, request authorization mappings, form login,
     * logout, exception handling, and the custom UserDetailsService.
     * </p>
     *
     * @param http the HttpSecurity to configure
     * @return the built SecurityFilterChain
     * @throws Exception if an error occurs while building the filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Enable default CORS configuration
                .cors(withDefaults())
                // Exclude specified endpoints from CSRF protection
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/api/**", "/uploads/**", "/"
                ))
                // Define URL authorization rules
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/css/**", "/", "/403", "/js/**", "/img/**",
                                "/uploads/**", "/login", "/register",
                                "/api/user-exists", "/form/register", "/public/**",
                                "/api/delete/cart-item/**", "/api/products/search"
                        ).permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                )
                // Configure form-based login
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", false)
                        .permitAll()
                )
                // Configure logout behavior
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                // Handle access denied exceptions
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")
                )
                // Use custom UserDetailsService for authentication
                .userDetailsService(customUserDetailsService);

        return http.build();
    }
}
