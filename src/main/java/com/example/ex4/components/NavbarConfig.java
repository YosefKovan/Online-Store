package com.example.ex4.components;

import com.example.ex4.repo.CartItem;
import com.example.ex4.repo.Category;
import com.example.ex4.repo.UserAccount;
import com.example.ex4.services.CategoryService;
import com.example.ex4.services.UserAccountService;
import com.example.ex4.session.CartSession;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.List;

/**
 * Configuration class responsible for setting up the navigation bar attributes.
 * <p>
 * This configuration populates the model with categories, cart information,
 * and user details for display in the application's navbar.
 * </p>
 */
@Configuration
public class NavbarConfig {

    /** Service for retrieving product categories. */
    @Autowired
    private CategoryService categoryService;

    /** Service for user account operations. */
    @Autowired
    private UserAccountService userAccountService;

    /** Session bean managing the user's shopping cart. */
    @Resource(name = "cartSessionBean")
    private CartSession cartSession;

    /**
     * Populates the model with navigation bar data.
     * <p>
     * Adds categories, cart size, and, if available, the current user's name to the model.
     * This method should be invoked before rendering views containing the navbar.
     * </p>
     *
     * @param model     the model to which navbar attributes are added
     * @param principal the security principal representing the currently authenticated user; may be null
     */
    public void setNavbar(Model model, Principal principal) {

        List<Category> categories = categoryService.getAllCategories();
        List<CartItem> cartItems = cartSession.getCartItems();

        if (principal != null) {
            UserAccount user = userAccountService.getByEmail(principal.getName());
            model.addAttribute("name", user.getUsername());
        }

        model.addAttribute("cartSize", cartSession.getCartSize());
        model.addAttribute("categories", categories);
    }

}
