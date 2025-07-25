package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Order entities.
 * <p>
 * Extends JpaRepository to provide CRUD operations and custom query
 * to retrieve orders by user account.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds all orders for the given user account.
     *
     * @param userAccount the user whose orders are to be retrieved
     * @return list of orders associated with the specified user
     */
    List<Order> findByUserAccount(UserAccount userAccount);

}
