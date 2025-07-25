package com.example.ex4.session;

import com.example.ex4.repo.CartItem;
import com.example.ex4.repo.CartItemRepository;
import com.example.ex4.repo.Product;
import com.example.ex4.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Session-scoped component for managing cart state per user session.
 * <p>
 * Stores and manipulates a list of {@link CartItem} objects,
 * providing operations to add, remove, and clear cart items,
 * as well as compute cart metrics like total price and size.
 * </p>
 */
@Component
public class CartSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Service for retrieving current product details from the database.
     */
    @Autowired
    private ProductService productService;

    /**
     * Internal list of {@link CartItem} entries in the cart.
     */
    private ArrayList<CartItem> cartItems;

    /**
     * Constructs an empty cart session.
     */
    public CartSession() {
        cartItems = new ArrayList<>();
    }

    /**
     * Finds a cart item by the given product ID.
     *
     * @param productId the ID of the product to find
     * @return the matching CartItem, or null if not found
     */
    private CartItem findCartItemByProductId(long productId) {
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                cartItem = item;
                break;
            }
        }
        return cartItem;
    }

    /**
     * Calculates the total price for all items in the cart.
     *
     * @return the sum of (price × quantity) for each cart item
     */
    public double getTotalCartPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            double productPrice = cartItem.getProduct().getPrice();
            int quantity = cartItem.getQuantity();
            totalPrice += productPrice * quantity;
        }
        return totalPrice;
    }

    /**
     * Replaces the current cart items list with the provided list.
     *
     * @param cartItems the new list of CartItem objects
     */
    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * Returns the list of cart items, ensuring each item's product is refreshed
     * from the database.
     *
     * @return the synchronized list of CartItem objects
     */
    public ArrayList<CartItem> getCartItems() {
        for (CartItem cartItem : cartItems) {
            long productId = cartItem.getProduct().getId();
            Product product = productService.getProductById(productId);
            cartItem.setProduct(product);
        }
        return cartItems;
    }

    /**
     * Adds the specified CartItem to the cart. If an item for the same product
     * already exists, its quantity is increased.
     *
     * @param cartItem  the CartItem to add
     * @param productId the ID of the product to add
     */
    public void add(CartItem cartItem, long productId) {
        CartItem existing = findCartItemByProductId(productId);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + cartItem.getQuantity());
        } else {
            Product product = productService.getProductById(productId);
            cartItem.setId(product.getId());
            cartItem.setProduct(product);
            cartItems.add(cartItem);
        }
    }

    /**
     * Removes the cart item associated with the given product ID.
     *
     * @param id the product ID of the item to remove
     * @throws Error if no matching cart item is found
     */
    public void remove(long id) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getProduct().getId() == id) {
                cartItems.remove(i);
                return;
            }
        }
        throw new Error("Cart Item To Delete Was Not Found");
    }

    /**
     * Clears all items from the cart.
     */
    public void clearCart() {
        cartItems.clear();
    }

    /**
     * Returns the total number of items in the cart (sum of quantities).
     *
     * @return the total quantity of items
     */
    public int getCartSize() {
        int cartItemCount = 0;
        for (CartItem cartItem : cartItems) {
            cartItemCount += cartItem.getQuantity();
        }
        return cartItemCount;
    }

}
