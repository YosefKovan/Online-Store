package com.example.ex4.services;

import com.example.ex4.repo.UserAccount;
import com.example.ex4.repo.UserAccountRepository;
import com.example.ex4.security.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing user accounts.
 * Handles retrieval, creation, and validation of {@link UserAccount} entities,
 * including password encoding and uniqueness checks for username and email.
 */
@Service
public class UserAccountService {

    /**
     * Repository for performing CRUD operations on user accounts.
     */
    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * Encoder for hashing user passwords before persisting.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user account by email.
     *
     * @param email the email address to look up
     * @return the found {@link UserAccount}
     * @throws ResponseStatusException if no account exists with the given email
     */
    public UserAccount getByEmail(String email) {
        return userAccountRepository
                .getUserAccountByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User Account not found"));
    }

    /**
     * Persists a new user account after encoding its password and assigning a role.
     *
     * @param userAccount the {@link UserAccount} entity to save
     * @param role        the role to assign (e.g., "ROLE_USER")
     */
    public void saveUserAccount(UserAccount userAccount, String role) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setRole(role);
        userAccountRepository.save(userAccount);
    }

    /**
     * Checks whether the provided username or email is already in use.
     * Returns HTTP 409 CONFLICT if either exists, otherwise HTTP 200 OK.
     *
     * @param userAccount the user data containing username and email to check
     * @return a {@link ResponseEntity} containing:
     *         <ul>
     *           <li>a map "errors" with flags for "username" and "email"</li>
     *           <li>a map "errorMessage" with corresponding error messages</li>
     *         </ul>
     */
    public ResponseEntity<?> checkUsernameOrEmailExist(UserAccount userAccount) {
        boolean username = userAccountRepository.existsByUsername(userAccount.getUsername());
        boolean email = userAccountRepository.existsByEmail(userAccount.getEmail());

        Map<String, Boolean> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);

        Map<String, String> errors = new HashMap<>();
        errors.put("usernameError", "Username already exists");
        errors.put("emailError", "Email already exists");

        Map<String, Object> payload = new HashMap<>();
        payload.put("errors", map);
        payload.put("errorMessage", errors);

        if (username || email) {
            return new ResponseEntity<>(payload, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(payload, HttpStatus.OK);
        }
    }

    /**
     * Retrieves a user account by its unique identifier.
     *
     * @param id the ID of the user account
     * @return the found {@link UserAccount}
     * @throws ResponseStatusException if no account exists with the given ID
     */
    public UserAccount getUserById(long id) {
        return userAccountRepository
                .getUserAccountById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User Account not found"));
    }

    /**
     * Verifies that no user account exists for the given email.
     *
     * @param email the email address to verify
     * @return true if the email is not associated with any existing account; false otherwise
     */
    public boolean checkExistsByEmail(String email) {
        return userAccountRepository.getUserAccountByEmail(email).isEmpty();
    }
}
