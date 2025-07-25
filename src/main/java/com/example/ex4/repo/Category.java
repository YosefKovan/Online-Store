package com.example.ex4.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a product category that can be assigned to products.
 * <p>
 * A category has a unique name and a collection of associated products.
 */
@Entity
public class Category implements Serializable {

    /**
     * Unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Unique name of the category.
     */
    @NotEmpty(message = "product name is mandatory")
    @Column(unique = true)
    private String categoryName;

    /**
     * Products belonging to this category.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    /**
     * Default constructor for JPA.
     */
    public Category() {
    }

    /**
     * Constructs a category with the specified name.
     *
     * @param categoryName the unique name of the category
     */
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Returns the unique name of the category.
     *
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the unique name of the category.
     *
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Returns the ID of the category.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the category.
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the list of products in this category.
     *
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Sets the list of products for this category.
     *
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
