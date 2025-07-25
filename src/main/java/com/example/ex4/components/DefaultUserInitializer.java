package com.example.ex4.components;

import com.example.ex4.repo.UserAccount;
import com.example.ex4.services.UserAccountService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * Component responsible for initializing the default admin user in the system.
 * <p>
 * Upon application startup, it checks whether an admin account exists by email,
 * and if not, creates one with the configured credentials and role.
 * </p>
 */

@Component
public class DefaultUserInitializer {

    /** Service providing operations for managing user accounts. */
    @Autowired
    private UserAccountService userAccountService;


    /**
     * Initializes the default admin user after bean construction.
     * <p>
     * This method is invoked automatically after the Spring context is initialized.
     * It checks if an admin account with the specified email exists, and if so,
     * creates and saves a new UserAccount with the ADMIN role.
     * </p>
     */

    @PostConstruct
    public void init() {

        if (userAccountService.checkExistsByEmail("admin@gmail.com")) {
            UserAccount admin = new UserAccount();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword("Test@2025");
            userAccountService.saveUserAccount(admin, "ADMIN");

            System.out.println("Admin created.");
        }
    }
}
