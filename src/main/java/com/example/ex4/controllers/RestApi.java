package com.example.ex4.controllers;

import com.example.ex4.dto.IdRequest;
import com.example.ex4.dto.ProductDto;
import com.example.ex4.repo.Category;
import com.example.ex4.repo.Product;
import com.example.ex4.repo.UserAccount;
import com.example.ex4.services.*;
import com.example.ex4.session.CartSession;
import jakarta.annotation.Resource;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST API controller for managing users, products, categories, and cart items.
 * <p>
 * Exposes endpoints for checking user existence, product CRUD operations,
 * category CRUD operations, cart item deletion, product search, and global exception handling.
 */
@RestController
@RequestMapping("/api")
public class RestApi {

    /**
     * Service for product-related operations.
     */
    @Autowired
    ProductService productService;

    /**
     * Service for category-related operations.
     */
    @Autowired
    CategoryService categoryService;

    /**
     * Service for user account-related operations.
     */
    @Autowired
    private UserAccountService userAccountService;

    /**
     * Session bean for managing the shopping cart.
     */
    @Resource(name = "cartSessionBean")
    private CartSession cartSession;

    /**
     * Checks if a user with the given username or email exists.
     *
     * @param user the user account to check
     * @return ResponseEntity indicating whether the user exists
     */
    @PostMapping("/user-exists")
    public ResponseEntity<?> checkUserExists(@RequestBody UserAccount user) {
        return userAccountService.checkUsernameOrEmailExist(user);
    }

    /**
     * Adds a new product via the admin endpoint.
     *
     * @param product the product to add
     * @return HTTP 200 OK if the product is successfully added
     * @throws IOException if file handling fails
     */
    @PostMapping("/admin/add-product")
    public ResponseEntity<?> addProductAdmin(@ModelAttribute Product product) throws IOException {
        productService.addProduct(product, product.getFile());
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves all products.
     *
     * @return ResponseEntity containing the list of all products
     */
    @GetMapping("/admin/get-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Deletes a product by ID via the admin endpoint.
     *
     * @param idRequest wrapper containing the product ID to delete
     * @return ResponseEntity containing the deleted ID
     * @throws IOException if deletion fails
     */
    @DeleteMapping("/admin/product-manager")
    public ResponseEntity<?> deleteProductAdmin(@RequestBody IdRequest idRequest) throws IOException {
        productService.deleteProduct(idRequest.getId());
        return ResponseEntity.ok(idRequest);
    }

    /**
     * Adds a new category via the admin endpoint.
     *
     * @param category the category to add
     * @param result   binding result for validation errors
     * @return ResponseEntity containing a map of categories after addition
     * @throws IOException if category creation fails
     * @throws Error      if validation errors are present
     */
    @PostMapping("/admin/category")
    public ResponseEntity<?> addCategoryAdmin(@Valid @RequestBody Category category, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            throw new Error("Unable to add category");
        }
        Map<String, Category> categoryMap = categoryService.addCategoryAndGetMap(category);
        return ResponseEntity.ok(categoryMap);
    }

    /**
     * Deletes a category by ID via the admin endpoint.
     *
     * @param idRequest wrapper containing the category ID to delete
     * @return ResponseEntity containing the deleted ID
     * @throws IOException if deletion fails
     */
    @DeleteMapping("/admin/category")
    public ResponseEntity<?> deleteCategory(@RequestBody IdRequest idRequest) throws IOException {
        categoryService.deleteCategory(idRequest.getId());
        return ResponseEntity.ok(idRequest);
    }

    /**
     * Deletes a cart item by its ID.
     *
     * @param id the ID of the cart item to remove
     * @return HTTP 200 OK if removal is successful
     * @throws IOException if removal fails
     */
    @DeleteMapping("/delete/cart-item/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) throws IOException {
        cartSession.remove(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Searches for products matching the given query string.
     *
     * @param query search term for product lookup
     * @return ResponseEntity containing a list of ProductDto matching the query
     */
    @GetMapping("/products/search")
    public ResponseEntity<?> search(@RequestParam String query) {
        List<Product> products = productService.getAllSearchItems(query);
        List<ProductDto> productsDto = new ArrayList<>();
        for (Product product : products) {
            productsDto.add(new ProductDto(product.getId(), product.getProductName()));
        }
        return ResponseEntity.ok(productsDto);
    }

    /**
     * Handles validation exceptions for method argument and constraint violations.
     *
     * @param ex exception containing method argument validation errors
     * @return map of field names to error messages
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Handles illegal argument exceptions and returns a bad request response.
     *
     * @param ex the exception thrown for a bad request
     * @return map containing error details and status code
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Request failed");
        error.put("statusCode", status.value());
        error.put("message", ex.getMessage());
        return error;
    }

    /**
     * Handles generic exceptions and returns an internal server error response.
     *
     * @param ex the exception thrown during request processing
     * @return map containing error details and status code
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleInternalServerError(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Request failed");
        error.put("statusCode", status.value());
        error.put("message", ex.getMessage());
        return error;
    }

    /**
     * Handles database access exceptions and returns an internal server error response.
     *
     * @param ex the DataAccessException thrown during database operations
     * @return map containing database error details and status code
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleDatabaseErrors(DataAccessException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Database error");
        error.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message", ex.getMostSpecificCause().getMessage());
        return error;
    }

    /**
     * Handles data integrity violations and returns a conflict response.
     *
     * @param ex the DataIntegrityViolationException thrown for constraint violations
     * @return map containing violation details and status code
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleConstraintViolation(DataIntegrityViolationException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Data Integrity Violation Exception");
        error.put("statusCode", HttpStatus.CONFLICT.value());
        error.put("message", ex.getMostSpecificCause().getMessage());
        return error;
    }
}
