package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing OrderItem entities.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations for OrderItem.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Inherits create, read, update, delete, and pagination methods from JpaRepository
}
