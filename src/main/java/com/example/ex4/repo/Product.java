package com.example.ex4.repo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a product available for purchase, including details such as name,
 * description, pricing, inventory, image, category, and associated reviews.
 */
@Entity
public class Product implements Serializable {

    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Human-readable name of the product; cannot be empty.
     */
    @NotNull
    @NotEmpty(message = "product name is mandatory")
    private String productName;

    /**
     * Detailed description of the product; cannot be empty.
     */
    @NotNull
    @NotEmpty(message = "product description is mandatory")
    private String productDescription;

    /**
     * Price of the product; must be zero or positive.
     */
    @PositiveOrZero(message = "price must be 0 or positive")
    private double price;

    /**
     * Number of items available in inventory; must be zero or positive.
     */
    @PositiveOrZero(message = "inventory must be 0 or positive")
    private int inventory;

    /**
     * URL pointing to the stored product image.
     */
    private String imageUrl;

    /**
     * Category to which this product belongs; JSON ignored to prevent circular references.
     */
    @JsonIgnore
    @ManyToOne
    private Category category;

    /**
     * Transient field to bind selected category ID from forms; must not be null when creating or updating products.
     */
    @Transient
    @NotNull(message = "You must choose a category")
    private long categoryId;

    /**
     * Transient file upload for product image; not persisted.
     */
    @Transient
    @JsonIgnore
    private MultipartFile file;

    /**
     * List of reviews submitted for this product.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    /**
     * Default constructor initializing default values and review collection.
     */
    public Product() {
        price = 0.0;
        inventory = 0;
        reviews = new ArrayList<>();
    }

    /**
     * Constructs a product with the specified details.
     *
     * @param productName        name of the product
     * @param productDescription description of the product
     * @param price              price of the product
     * @param inventory          starting inventory count
     */
    public Product(String productName, String productDescription, double price, int inventory) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.inventory = inventory;
    }

    // Setter And Getters

    /**
     * Returns the unique identifier of the product.
     *
     * @return product ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the product.
     *
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the name of the product.
     *
     * @return product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the name of the product.
     *
     * @param productName the name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Returns the uploaded file for the product image.
     *
     * @return multipart file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * Sets the file for the product image upload.
     *
     * @param file the multipart file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * Returns the product description.
     *
     * @return product description
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the product description.
     *
     * @param productDescription the description to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * Returns the price of the product.
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the inventory count.
     *
     * @return inventory count
     */
    public int getInventory() {
        return inventory;
    }

    /**
     * Sets the inventory count.
     *
     * @param inventory the count to set
     */
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns the associated category.
     *
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the associated category.
     *
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the URL of the product image.
     *
     * @return image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL of the product image.
     *
     * @param imageUrl the URL to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the ID of the selected category.
     *
     * @return category ID
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the ID of the selected category.
     *
     * @param categoryName the ID to set
     */
    public void setCategoryId(long categoryName) {
        this.categoryId = categoryName;
    }

    /**
     * Returns the list of reviews for this product.
     *
     * @return list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for this product.
     *
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    // Other Functions

    /**
     * Calculates the average rating of all reviews.
     *
     * @return integer average rating, or 0 if no reviews
     */
    public int getReviewsAverage() {
        if (reviews == null || reviews.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (Review review : reviews) {
            total += review.getRating();
        }

        return total / reviews.size();
    }
}
