package com.example.ex4.services;

import com.example.ex4.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Service class for managing {@link Review} entities.
 * <p>
 * Provides functionality to add reviews for products and retrieve existing reviews.
 * </p>
 */
@Service
public class ReviewService {

    /**
     * Repository for performing CRUD operations on reviews.
     */
    @Autowired
    private ReviewRepository reviewRepo;

    /**
     * Service for retrieving product details when associating reviews.
     */
    @Autowired
    private ProductService productService;

    /**
     * Service for retrieving user account details when associating reviews.
     */
    @Autowired
    private UserAccountService userAccountService;

    /**
     * Adds a new review to the specified product by the authenticated user.
     *
     * @param productId the identifier of the product being reviewed
     * @param review    the review entity containing rating and comments
     * @param principal the security principal representing the authenticated user
     */
    public void addReview(Long productId, Review review, Principal principal) {
        // Retrieve the product and user account
        Product product = productService.getProductById(productId);
        UserAccount userAccount = userAccountService.getByEmail(principal.getName());

        // Associate review with product and user
        review.setProduct(product);
        review.setUserAccount(userAccount);

        // Persist review
        reviewRepo.save(review);
    }

    /**
     * Retrieves all reviews for a given product.
     *
     * @param productId the identifier of the product
     * @return list of reviews associated with the product
     */
    public List<Review> getReviewsForProduct(Long productId) {
        return reviewRepo.findByProductId(productId);
    }

}
