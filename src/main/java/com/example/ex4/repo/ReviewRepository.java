package com.example.ex4.repo;

import com.example.ex4.repo.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Review entities.
 * <p>
 * Extends JpaRepository to provide CRUD operations and a custom finder
 * for reviews by product ID.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Retrieves all reviews associated with a specific product.
     *
     * @param productId the ID of the product
     * @return list of Review entities for the given product
     */
    List<Review> findByProductId(Long productId);

}
