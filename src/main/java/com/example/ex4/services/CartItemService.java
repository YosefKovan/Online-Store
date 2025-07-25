package com.example.ex4.services;

import com.example.ex4.repo.CartItem;
import com.example.ex4.repo.CartItemRepository;
import com.example.ex4.repo.Product;
import com.example.ex4.repo.UserAccount;
import com.example.ex4.session.CartSession;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

/**
 * Service for managing operations related to cart items.
 * <p>
 * Provides functionality to add, remove, and retrieve cart items
 * for authenticated users by coordinating between the repository,
 * user account service, and product service.
 */
@Service
public class CartItemService {

    /**
     * Repository for performing CRUD operations on CartItem entities.
     */
    @Autowired
    private CartItemRepository cartItemRepository;

    /**
     * Service for retrieving and managing UserAccount entities.
     */
    @Autowired
    private UserAccountService userAccountService;

    /**
     * Service for retrieving and managing Product entities.
     */
    @Autowired
    private ProductService productService;

    /**
     * Session bean for maintaining the current user's cart state.
     */
    @Resource(name = "cartSessionBean")
    private CartSession cartSession;

}
