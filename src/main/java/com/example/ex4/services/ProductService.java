package com.example.ex4.services;

import com.example.ex4.repo.Category;
import com.example.ex4.repo.Product;
import com.example.ex4.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

/**
 * Service class for managing {@link Product} entities.
 * <p>
 * Provides operations for adding, editing, deleting, and retrieving products,
 * as well as handling image storage and inventory adjustments.
 * </p>
 */
@Service
public class ProductService {

    /**
     * Repository for performing CRUD operations on products.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Service responsible for saving and deleting product images.
     */
    @Autowired
    private StorageService storageService;

    /**
     * Service for retrieving and managing category information.
     */
    @Autowired
    private CategoryService categoryService;

    //========================================
    //            Public Methods
    //========================================

    /**
     * Adds a new product, storing its image and associating it with the correct category.
     *
     * @param product the product entity populated from the form model
     * @param file    the multipart image file to save
     * @throws IOException if an error occurs during image storage
     */
    public void addProduct(@ModelAttribute Product product, MultipartFile file) throws IOException {
        storageService.saveImage(file);
        product.setImageUrl(file.getOriginalFilename());
        Category category = categoryService.getCategoryById(product.getCategoryId());
        product.setCategory(category);
        productRepository.save(product);
    }

    /**
     * Edits an existing product, updating its details and replacing its image if a new one is provided.
     *
     * @param product the product entity populated from the form model
     * @param id      the identifier of the product to edit
     * @throws IOException if an error occurs during image storage or deletion
     */
    public void editProduct(@ModelAttribute Product product, long id) throws IOException {
        product.setId(id);
        MultipartFile file = product.getFile();
        Product existing = getProductById(id);

        // Update category association
        Category category = categoryService.getCategoryById(product.getCategoryId());
        product.setCategory(category);

        if (file.isEmpty()) {
            // Preserve existing image if no new file provided
            product.setImageUrl(existing.getImageUrl());
            productRepository.save(product);
        } else {
            // Delete old image and save new one
            if (existing.getImageUrl() != null) {
                storageService.deleteImage(existing.getImageUrl());
            }
            addProduct(product, file);
        }
    }

    /**
     * Deletes the specified product and its associated image file.
     *
     * @param id the identifier of the product to delete
     * @throws IOException if an error occurs during image deletion
     */
    public void deleteProduct(@ModelAttribute long id) throws IOException {
        Product product = getProductById(id);
        String imageUrl = product.getImageUrl();
        productRepository.deleteById(id);
        storageService.deleteImage(imageUrl);
    }

    /**
     * Retrieves all products in the system.
     *
     * @return a list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its identifier.
     *
     * @param id the identifier of the product
     * @return the matching product
     * @throws ResponseStatusException if no product is found with the given id
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    /**
     * Retrieves products belonging to the specified category name.
     *
     * @param categoryString the name of the category to filter by
     * @return a list of products matching the category
     */
    public List<Product> getProductsByCategory(String categoryString) {
        Category category = categoryService.getCategoryByName(categoryString);
        return productRepository.findByCategory(category);
    }

    /**
     * Checks if the requested quantity is available and reduces the inventory accordingly.
     *
     * @param quantity the amount to check and deduct
     * @param product  the product entity whose inventory to adjust
     * @return true if inventory was sufficient and reduced, false otherwise
     */
    public boolean checkAndReduceQuantity(int quantity, Product product) {
        if (product.getInventory() <= quantity) {
            return false;
        }
        product.setInventory(product.getInventory() - quantity);
        return true;
    }

    /**
     * Reduces the product's inventory by the specified amount and saves the update.
     *
     * @param quantity the amount to deduct from inventory
     * @param product  the product entity to update
     */
    public void updateProductQuantity(int quantity, Product product) {
        product.setInventory(product.getInventory() - quantity);
        productRepository.save(product);
    }

    /**
     * Searches for products containing the given term in their name, returning up to five results.
     *
     * @param searched the search term to filter product names
     * @return list of matching products (max 5)
     */
    public List<Product> getAllSearchItems(String searched) {
        return productRepository.findTop5ByProductNameContainingIgnoreCase(searched);
    }

}
