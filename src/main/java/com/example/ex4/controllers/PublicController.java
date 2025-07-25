package com.example.ex4.controllers;

import com.example.ex4.components.NavbarConfig;
import com.example.ex4.repo.CartItem;
import com.example.ex4.repo.Product;
import com.example.ex4.services.*;
import com.example.ex4.session.CartSession;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller responsible for handling public-facing routes such as
 * product browsing, category listing, cart display, and adding products to cart.
 */
@Controller
@RequestMapping("/public")
public class PublicController {

    /** Service layer for retrieving product data. */
    @Autowired
    ProductService productService;

    /** Utility component for setting up navigation bar attributes. */
    @Autowired
    NavbarConfig navbarConfig;

    /** Session bean that holds the cart items for the current user session. */
    @Resource(name = "cartSessionBean")
    private CartSession cartSession;

    /**
     * Adds a product to the user's cart and redirects back to its category page.
     *
     * @param cartItem   item and quantity to add
     * @param productId  ID of the product to add
     * @return redirect URL to the category page of the product
     */
    @PostMapping("/add-to-cart/{productId}")
    public String addToCart(@ModelAttribute CartItem cartItem, @PathVariable long productId) {
        cartSession.add(cartItem, productId);
        Product product = productService.getProductById(productId);
        return "redirect:/public/categories/" + product.getCategory().getCategoryName();
    }

    /**
     * Displays all products under a specified category.
     *
     * @param categoryName name of the category to display
     * @param model        model to hold attributes for rendering
     * @param principal    currently authenticated user (if any)
     * @return view name of the products page
     */
    @GetMapping("/categories/{categoryName}")
    public String getCategoryPage(@PathVariable String categoryName, Model model, Principal principal) {
        navbarConfig.setNavbar(model, principal);
        List<Product> products = productService.getProductsByCategory(categoryName);
        model.addAttribute("products", products);
        return "products-page";
    }

    /**
     * Shows detailed information for a single product.
     *
     * @param productId ID of the product to display
     * @param model     model to hold product data
     * @param principal currently authenticated user (if any)
     * @return view name of the product detail page
     */
    @GetMapping("/product/{productId}")
    String getProductPage(@PathVariable long productId, Model model, Principal principal) {
        navbarConfig.setNavbar(model, principal);
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        model.addAttribute("average", product.getReviewsAverage());
        model.addAttribute("reviews", product.getReviews());
        return "product-page";
    }

    /**
     * Displays the shopping cart contents.
     *
     * @param model     model to hold cart items and total price
     * @param principal currently authenticated user (if any)
     * @return view name of the cart page
     */
    @GetMapping("/cart")
    String getCartPage(Model model, Principal principal) {
        navbarConfig.setNavbar(model, principal);
        List<CartItem> cartItems = cartSession.getCartItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartSession.getTotalCartPrice());
        return "cart";
    }
}
