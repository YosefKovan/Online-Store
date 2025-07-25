package com.example.ex4.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * Represents a user account in the application, holding authentication
 * and authorization details such as username, email, password, and role.
 */
@Entity
public class UserAccount {

    /**
     * Unique identifier for the user account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Role assigned to the user (e.g., "USER", "ADMIN").
     */
    @NotEmpty(message = "Role is required")
    private String role;

    /**
     * Unique username for login.
     */
    @NotEmpty(message = "Username is required")
    @Column(unique = true)
    private String username;

    /**
     * Unique email for the user; must be a valid email format.
     */
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    /**
     * Password for the account; must be at least 8 characters long,
     * include an uppercase letter, and a digit or special character.
     */
    @NotEmpty(message = "Password is required")
    @Pattern(
            regexp = "^(?=.{8,})(?=.*[A-Z])(?=.*[^A-Za-z]).*$",
            message = "Password must be at least 8 characters, include an uppercase letter, and a digit or special character"
    )
    private String password;

    /**
     * Transient field for confirming the password during registration;
     * not persisted to the database.
     */
    @Transient
    private String confirmPassword;

    /**
     * Default no-arg constructor required by JPA.
     */
    public UserAccount() {
    }

    /**
     * Constructs a new UserAccount with the given details.
     *
     * @param username the username for login
     * @param sellerEmail the email address of the user
     * @param password the raw password for authentication
     * @param role the role to assign
     */
    public UserAccount(String username, String sellerEmail, String password, String role) {
        this.username = username;
        this.email = sellerEmail;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the unique identifier of this account.
     *
     * @return the account ID
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the role assigned to this user.
     *
     * @return the user role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role for this user.
     *
     * @param role the role to assign
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the username of this account.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this account.
     *
     * @param sellerName the username to set
     */
    public void setUsername(String sellerName) {
        this.username = sellerName;
    }

    /**
     * Returns the email of this account.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for this account.
     *
     * @param sellerEmail the email to set
     */
    public void setEmail(String sellerEmail) {
        this.email = sellerEmail;
    }

    /**
     * Returns the password of this account.
     *
     * @return the encoded password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for this account.
     *
     * @param password the raw password to encode and store
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the confirmation password entered by the user.
     *
     * @return the confirmPassword field
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets the confirmation password for validation purposes.
     *
     * @param confirmPassword the confirmation password to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
