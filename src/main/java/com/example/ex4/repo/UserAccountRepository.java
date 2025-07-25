package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing UserAccount entities.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom lookups
 * by email, username, and ID, along with existence checks.
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * Retrieves a user account by email.
     *
     * @param email the email to search for
     * @return an Optional containing the UserAccount if found, or empty otherwise
     */
    Optional<UserAccount> getUserAccountByEmail(String email);

    /**
     * Retrieves a user account by username.
     *
     * @param username the username to search for
     * @return an Optional containing the UserAccount if found, or empty otherwise
     */
    Optional<UserAccount> getUserAccountByUsername(String username);

    /**
     * Retrieves a user account by ID.
     *
     * @param id the ID of the user account
     * @return an Optional containing the UserAccount if found, or empty otherwise
     */
    Optional<UserAccount> getUserAccountById(long id);

    /**
     * Checks if a user account exists with the given email.
     *
     * @param email the email to check
     * @return true if an account with the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user account exists with the given username.
     *
     * @param username the username to check
     * @return true if an account with the username exists, false otherwise
     */
    boolean existsByUsername(String username);

}
