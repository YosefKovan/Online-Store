package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Category entities.
 * <p>
 * Provides CRUD operations and lookup by category name.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds a category by its unique name.
     *
     * @param name the unique name of the category
     * @return an Optional containing the matching Category if found, or empty otherwise
     */
    Optional<Category> findByCategoryName(String name);

}
