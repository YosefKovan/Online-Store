package com.example.ex4.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * Entity representing a product review submitted by a user.
 * Captures rating, reviewer name, title, comment text, creation timestamp,
 * and links to the reviewed product and submitting user account.
 */
@Entity
public class Review implements Serializable {

    /**
     * Unique identifier for the review.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Timestamp when the review was created.
     */
    private LocalDateTime createdAt;

    /**
     * Rating provided by the user; must be between 1 and 5.
     */
    @Min(1)
    @Max(5)
    private int rating;

    /**
     * Name of the reviewer.
     */
    @NotEmpty
    private String name;

    /**
     * Title of the review comment; cannot be empty.
     */
    @NotEmpty(message = "Comment title is mandatory!")
    private String commentTitle;

    /**
     * Detailed review comment; mapped to column 'product_review'.
     */
    @Column(name = "product_review", nullable = false)
    private String comment;

    /**
     * The product this review refers to.
     */
    @ManyToOne
    private Product product;

    /**
     * The user account that submitted this review.
     */
    @ManyToOne
    private UserAccount userAccount;

    /**
     * Default constructor initializes the creation timestamp.
     */
    public Review() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Returns the unique identifier of the review.
     * @return review ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the rating given in the review.
     * @return rating (1–5)
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating for the review.
     * @param rating rating to set (1–5)
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Returns the detailed review comment.
     * @return comment text
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the detailed review comment.
     * @param comment comment text to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns the creation timestamp of the review.
     * @return creation time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the product associated with this review.
     * @return Product entity
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with this review.
     * @param product product to associate
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Returns the user account that submitted this review.
     * @return UserAccount entity
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * Sets the user account that submitted this review.
     * @param userAccount user account to associate
     */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * Returns the title of the review comment.
     * @return comment title
     */
    public String getCommentTitle() {
        return commentTitle;
    }

    /**
     * Sets the title of the review comment.
     * @param commentTitle title to set
     */
    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    /**
     * Returns the name of the reviewer.
     * @return reviewer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the reviewer.
     * @param name reviewer name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
