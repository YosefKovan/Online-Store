package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for managing CartItem entities.
 * <p>
 * Extends JpaRepository to provide CRUD operations and
 * custom queries for cart items associated with user accounts.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Finds all cart items for the given user account.
     *
     * @param userAccount the user whose cart items are to be retrieved
     * @return a list of CartItem entities belonging to the specified user
     */
    List<CartItem> getByUserAccount(UserAccount userAccount);

}
