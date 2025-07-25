package com.example.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Product entities.
 * <p>
 * Provides CRUD operations and custom queries for products.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

     /**
      * Retrieves a product by its ID.
      *
      * @param id the ID of the product
      * @return an Optional containing the product if found, otherwise empty
      */
     Optional<Product> findById(long id);

     /**
      * Finds all products in the specified category.
      *
      * @param category the category to filter products by
      * @return a list of products belonging to the given category
      */
     List<Product> findByCategory(Category category);

     /**
      * Retrieves the top 5 products whose names contain the given keyword, ignoring case.
      *
      * @param name the substring to match in product names
      * @return a list of up to 5 matching products
      */
     List<Product> findTop5ByProductNameContainingIgnoreCase(String name);

}
