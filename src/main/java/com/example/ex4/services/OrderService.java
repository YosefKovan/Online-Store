package com.example.ex4.services;

import com.example.ex4.repo.*;
import com.example.ex4.session.CartSession;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing {@link Order} entities and related operations.
 * <p>
 * Provides functionality to create, retrieve, and analyze orders,
 * including setting order items, associating user accounts,
 * and calculating metrics such as total orders and revenue.
 * </p>
 */
@Service
public class OrderService {

    /**
     * Repository for performing CRUD operations on orders.
     */
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Service for managing order items, used when setting items on an order.
     */
    @Autowired
    private OrderItemService orderItemService;

    /**
     * Service for managing products, used to update product quantities when creating orders.
     */
    @Autowired
    private ProductService productService;

    /**
     * Service for retrieving user account information by email.
     */
    @Autowired
    private UserAccountService userAccountService;

    /**
     * Session-scoped bean for maintaining the shopping cart state.
     */
    @Resource(name = "cartSessionBean")
    private CartSession cartSession;

    /**
     * Associates the given order with a {@link UserAccount} retrieved by email.
     *
     * @param order the order to set the user account on
     * @param email the email address of the user account
     */
    private void setUserAccount(Order order, String email) {
        userAccountService.getByEmail(email);
        order.setUserAccount(userAccountService.getByEmail(email));
    }

    //=====================================
    //           Public Methods
    //=====================================

    /**
     * Populates the given order with order items based on the current cart session.
     * <p>
     * For each item in the cart, updates the product stock quantity and
     * creates a corresponding {@link OrderItem} associated with the order.
     * </p>
     *
     * @param order the order to populate with items
     */
    public void setOrderItems(Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        cartSession.getCartItems().forEach(cartItem -> {
            productService.updateProductQuantity(cartItem.getQuantity(), cartItem.getProduct());
            orderItems.add(orderItemService.createOrderItem(cartItem, order));
        });
        order.setOrderItems(orderItems);
    }

    /**
     * Creates and saves a new order transactionally,
     * linking the order with the authenticated user and cart items.
     * <p>
     * Clears the cart session upon successful save.
     * </p>
     *
     * @param order     the order entity to add
     * @param principal the security principal representing the authenticated user
     */
    @Transactional
    public void addOrder(Order order, Principal principal) {
        setUserAccount(order, principal.getName());
        setOrderItems(order);
        orderRepository.save(order);
        cartSession.clearCart();
    }

    /**
     * Retrieves all orders associated with the user identified by the given email.
     *
     * @param email the email address of the user
     * @return list of orders belonging to the user
     */
    public List<Order> getAllOrders(String email) {
        return orderRepository.findByUserAccount(userAccountService.getByEmail(email));
    }

    /**
     * Returns the total number of orders in the system.
     *
     * @return total count of all orders
     */
    public int getTotalOrdersAmount() {
        return orderRepository.findAll().size();
    }

    /**
     * Calculates the total revenue generated from all orders.
     *
     * @return sum of total payments for all orders
     */
    public double getTotalRevenue() {
        double totalRevenue = 0;
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            totalRevenue += order.getTotalPayment();
        }
        return totalRevenue;
    }

}