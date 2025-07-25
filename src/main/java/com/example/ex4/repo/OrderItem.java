package com.example.ex4.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

/**
 * Represents an item within an order, linking a product to its ordered quantity.
 */
@Entity
public class OrderItem {

    /**
     * Unique identifier for the order item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The product associated with this order item.
     */
    @ManyToOne
    private Product product;

    /**
     * Quantity of the product ordered; must be at least 1.
     */
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    /**
     * The order that this item belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Default constructor for JPA.
     */
    public OrderItem() {
    }

    /**
     * Constructs an OrderItem with the specified product and quantity.
     *
     * @param product  the product being ordered
     * @param quantity the quantity of the product
     */
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Returns the unique identifier of this order item.
     *
     * @return the order item ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this order item.
     *
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the product linked to this order item.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product for this order item.
     *
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Returns the quantity ordered of the product.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product ordered.
     *
     * @param quantity the quantity to set; must be at least 1
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the order to which this item belongs.
     *
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the order reference for this item.
     *
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }
}