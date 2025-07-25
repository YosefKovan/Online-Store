package com.example.ex4.security;

import com.example.ex4.repo.UserAccount;
import com.example.ex4.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of Spring Security's {@link UserDetailsService} that
 * loads user information from the application's {@link UserAccountService}
 * using email as the username.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Service for retrieving {@link UserAccount} entities.
     */
    @Autowired
    private UserAccountService userAccountService;

    /**
     * Locates the user by their email address.
     * <p>
     * This method is called by Spring Security during authentication.
     * It uses {@link UserAccountService#getByEmail(String)} to fetch
     * the {@code UserAccount}. If no account is found, a
     * {@link UsernameNotFoundException} is thrown.
     * </p>
     *
     * @param email the email address of the user attempting to authenticate
     * @return a {@link UserDetails} object with username, password, and roles
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountService.getByEmail(email);

        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return User.withUsername(userAccount.getEmail())
                .password(userAccount.getPassword())
                .roles(userAccount.getRole())
                .build();
    }
}
