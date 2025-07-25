package com.example.ex4.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Represents an item in a user's shopping cart.
 * <p>
 * Contains quantity information along with the referenced product and the user account.
 */

@Entity
public class CartItem {

    /**
     * Unique identifier for the cart item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Quantity of the product in the cart. Must be zero or positive.
     */
    @PositiveOrZero
    @NotEmpty(message = "price is mandatory")
    private int quantity;

    /**
     * The product associated with this cart item.
     */
    @ManyToOne
    private Product product;

    /**
     * The user account to which this cart item belongs.
     */
    @ManyToOne
    private UserAccount userAccount;

    /**
     * Default constructor for JPA.
     */
    public CartItem() {
    }

    /**
     * Constructs a new CartItem with the specified quantity, product, and user account.
     *
     * @param quantity    the number of items
     * @param product     the product being added
     * @param userAccount the owner of the cart item
     */
    public CartItem(int quantity, Product product, UserAccount userAccount) {
        this.quantity = quantity;
        this.product = product;
        this.userAccount = userAccount;
    }

    /**
     * Retrieves the cart item ID.
     *
     * @return the cart item ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the cart item ID.
     *
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the quantity of the product in the cart.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in the cart.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieves the associated product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the associated product.
     *
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Retrieves the user account that owns this cart item.
     *
     * @return the user account
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * Sets the user account that owns this cart item.
     *
     * @param userAccount the user account to set
     */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
