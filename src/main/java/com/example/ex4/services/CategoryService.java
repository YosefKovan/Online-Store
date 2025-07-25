package com.example.ex4.services;

import com.example.ex4.repo.Category;
import com.example.ex4.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for managing product categories.
 * <p>
 * Provides operations to create, retrieve, and delete categories,
 * and to wrap responses in a standardized format when needed.
 * </p>
 */
@Service
public class CategoryService {

    /**
     * Repository for Category entity CRUD operations.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Persists a new category to the database.
     *
     * @param category the Category to add
     * @return the saved Category entity
     */
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Adds a category and returns a map containing the saved entity.
     * <p>
     * Useful for endpoints that expect a JSON response with a 'category' key.
     * </p>
     *
     * @param category the Category to add
     * @return a map with a single entry: 'category' → saved Category
     */
    public Map<String, Category> addCategoryAndGetMap(Category category) {
        Category dbCategory = addCategory(category);
        Map<String, Category> response = new HashMap<>();
        response.put("category", dbCategory);
        return response;
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     * @throws ResponseStatusException with status NOT_FOUND if the category does not exist
     */
    public void deleteCategory(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        categoryRepository.deleteById(id);
    }

    /**
     * Retrieves all categories in the system.
     *
     * @return list of all Category entities
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Finds a category by its unique name.
     *
     * @param name the name of the category
     * @return the matching Category entity
     * @throws ResponseStatusException with status NOT_FOUND if no category is found
     */
    public Category getCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    /**
     * Finds a category by its ID.
     *
     * @param id the ID of the category
     * @return the matching Category entity
     * @throws ResponseStatusException with status NOT_FOUND if no category is found
     */
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
}
