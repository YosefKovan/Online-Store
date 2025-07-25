package com.example.ex4.services;

import com.example.ex4.repo.*;
import com.example.ex4.session.CartSession;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing {@link OrderItem} entities.
 * <p>
 * Provides operations for creating, saving, and retrieving order items.
 * </p>
 */
@Service
public class OrderItemService {

    /**
     * Repository for performing CRUD operations on order items.
     */
    @Autowired
    private OrderItemRepository orderItemRepository;

    /**
     * Service for managing products, used to retrieve product details when creating order items.
     */
    @Autowired
    private ProductService productService;

    /**
     * Session-scoped bean for maintaining the shopping cart state.
     */
    @Resource(name = "cartSessionBean")
    private CartSession cartSession;

    /**
     * Persists the given {@link OrderItem}.
     *
     * @param orderItem the order item to save
     */
    public void saveItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    /**
     * Creates a new {@link OrderItem} from a {@link CartItem} and associates it with the given {@link Order}.
     *
     * @param cartItem the cart item containing product and quantity information
     * @param order    the order to which the item will be added
     * @return a new order item populated with product, quantity, and parent order reference
     */
    public OrderItem createOrderItem(CartItem cartItem, Order order) {

        OrderItem item = new OrderItem();
        item.setProduct(cartItem.getProduct());
        item.setQuantity(cartItem.getQuantity());
        item.setOrder(order); // set the parent reference!

        return item;
    }

    /**
     * Retrieves all persisted {@link OrderItem} instances.
     *
     * @return a list of all order items
     */
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
}
