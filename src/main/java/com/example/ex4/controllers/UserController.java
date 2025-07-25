package com.example.ex4.controllers;

import com.example.ex4.components.NavbarConfig;
import com.example.ex4.repo.Order;
import com.example.ex4.repo.Review;
import com.example.ex4.services.OrderService;
import com.example.ex4.services.ProductService;
import com.example.ex4.services.ReviewService;
import com.example.ex4.session.CartSession;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * MVC controller handling user-specific operations such as payment,
 * order history, and adding product reviews.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Service for managing orders.
     */
    @Autowired
    OrderService orderService;

    /**
     * Configuration component for setting up the navigation bar.
     */
    @Autowired
    NavbarConfig navbarConfig;

    /**
     * Service for managing reviews.
     */
    @Autowired
    private ReviewService reviewService;

    /**
     * Session bean for managing shopping cart state.
     */
    @Resource(name = "cartSessionBean")
    private CartSession cartSession;

    /**
     * Displays the payment page with current cart total.
     *
     * @param model     Spring MVC model
     * @param principal authenticated user principal
     * @return name of the payment-page view
     */
    @GetMapping("/payment-page")
    public String getPaymentPage(Model model, Principal principal) {
        navbarConfig.setNavbar(model, principal);
        model.addAttribute("order", new Order());
        model.addAttribute("total", cartSession.getTotalCartPrice());
        return "payment-page";
    }

    /**
     * Displays a list of the user's previous orders.
     *
     * @param model     Spring MVC model
     * @param principal authenticated user principal
     * @return name of the previous-orders view
     */
    @GetMapping("/previous-orders")
    public String getPreviousOrders(Model model, Principal principal) {
        navbarConfig.setNavbar(model, principal);
        List<Order> orders = orderService.getAllOrders(principal.getName());
        model.addAttribute("orders", orders);
        return "previous-orders";
    }

    /**
     * Processes the payment form submission and creates a new order.
     *
     * @param order     the order data from the form
     * @param result    binding result for validation errors
     * @param principal authenticated user principal
     * @param model     Spring MVC model
     * @return payment success view if valid, otherwise the payment page view
     */
    @PostMapping("/pay")
    public String postPaymentPage(@Valid @ModelAttribute Order order,
                                  BindingResult result,
                                  Principal principal,
                                  Model model) {
        if (result.hasErrors()) {
            navbarConfig.setNavbar(model, principal);
            model.addAttribute("total", cartSession.getTotalCartPrice());
            return "payment-page";
        }
        orderService.addOrder(order, principal);
        return "payment-success";
    }

    /**
     * Displays the add-review page for a specific order item.
     *
     * @param id        the ID of the order item to review
     * @param model     Spring MVC model
     * @param principal authenticated user principal
     * @return name of the add-review view
     */
    @GetMapping("/review/add-review/{id}")
    public String getReviewPage(@PathVariable long id,
                                Model model,
                                Principal principal) {
        model.addAttribute("url", "/user/review/add-review/" + id);
        navbarConfig.setNavbar(model, principal);
        return "add-review";
    }

    /**
     * Handles submission of a new review for a specific order item.
     *
     * @param review    the review data from the form
     * @param id        the ID of the order item being reviewed
     * @param principal authenticated user principal
     * @return redirect to the previous-orders page
     */
    @PostMapping("/review/add-review/{id}")
    public String postReview(@ModelAttribute Review review,
                             @PathVariable long id,
                             Principal principal) {
        reviewService.addReview(id, review, principal);
        return "redirect:/user/previous-orders";
    }
}
