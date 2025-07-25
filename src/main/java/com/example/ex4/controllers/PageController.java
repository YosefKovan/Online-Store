package com.example.ex4.controllers;

import com.example.ex4.components.NavbarConfig;
import com.example.ex4.repo.UserAccount;
import com.example.ex4.services.UserAccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

/**
 * Controller for handling public-facing pages such as landing, login,
 * registration, and access-denied error pages. It integrates navbar setup
 * and user registration logic.
 */
@Controller
public class PageController {

    /**
     * Configuration component for preparing navbar attributes.
     */
    @Autowired
    private NavbarConfig navbarConfig;

    /**
     * Service handling user account operations like registration.
     */
    @Autowired
    private UserAccountService userAccountService;

    /**
     * Displays the landing page and populates the navbar model attributes.
     *
     * @param model     the model to populate view attributes
     * @param principal the security principal of the authenticated user; may be null
     * @return the logical view name for the landing page
     */
    @GetMapping("/")
    public String landingPage(Model model, Principal principal) {
        navbarConfig.setNavbar(model, principal);
        return "landing";
    }

    /**
     * Displays the custom sign-in page.
     *
     * @return the logical view name for the sign-in page
     */
    @GetMapping("/login")
    public String login() {
        return "sign-in";
    }

    /**
     * Displays the user registration page.
     *
     * @return the logical view name for the registration page
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Handles new user registration form submission.
     * Creates a USER account and redirects to the login page with a success flag.
     *
     * @param userAccount the UserAccount model populated from the form
     * @return redirect URL to the login page
     */
    @PostMapping("/register")
    public String addUser(@ModelAttribute UserAccount userAccount) {
        userAccountService.saveUserAccount(userAccount, "USER");
        return "redirect:/login?registered=true";
    }

    /**
     * Displays a custom error page for HTTP 403 (Forbidden) statuses.
     *
     * @param model the model to populate error attributes
     * @return the logical view name for the error page
     */
    @GetMapping("/403")
    public String accessDenied(Model model) {
        model.addAttribute("errorCode", HttpStatus.FORBIDDEN.value());
        model.addAttribute("errorMessage", "Access Denied");
        return "error";
    }
}
