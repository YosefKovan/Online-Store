package com.example.ex4.controllers;

import com.example.ex4.repo.Category;
import com.example.ex4.repo.CartItem;
import com.example.ex4.repo.OrderItem;
import com.example.ex4.repo.Product;
import com.example.ex4.services.CategoryService;
import com.example.ex4.services.OrderItemService;
import com.example.ex4.services.OrderService;
import com.example.ex4.services.ProductService;
import com.example.ex4.session.CartSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * Controller responsible for administrative operations such as managing products,
 * categories, and orders within the admin section of the application.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /** Service for managing products. */
    @Autowired
    private ProductService productService;

    /** Service for managing product categories. */
    @Autowired
    private CategoryService categoryService;

    /** Service for retrieving order items. */
    @Autowired
    private OrderItemService orderItemService;

    /** Service for processing orders and revenue reports. */
    @Autowired
    private OrderService orderService;

    /**
     * Displays the product management page with all existing products.
     *
     * @param model the model to populate view attributes
     * @return the view name for product management
     */
    @GetMapping("/products-manager")
    public String sellerPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("url", "/admin/add-product-page");
        return "product-manager";
    }

    /**
     * Prepares and displays the add product page with available categories.
     *
     * @param model the model to populate view attributes
     * @return the view name for adding or editing a product
     */
    @GetMapping("/add-product-page")
    public String addProductPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "add-edit-product";
    }

    /**
     * Prepares and displays the edit product page for a given product.
     *
     * @param id    the ID of the product to edit
     * @param model the model to populate view attributes
     * @return the view name for adding or editing a product
     */
    @GetMapping("/add-product-page/edit/{id}")
    public String editProductPage(@PathVariable("id") long id, Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("url", "/admin/add-product-page/edit/" + id);
        return "add-edit-product";
    }

    /**
     * Handles submission of a new product.
     *
     * @param product the product entity to validate and save
     * @param result  binding result for validation errors
     * @param model   the model to repopulate attributes on error
     * @return redirect to product management on success or the add-edit view on error
     * @throws IOException if file upload fails
     */
    @PostMapping("/add-product-page")
    public String addProduct(
            @Valid @ModelAttribute Product product,
            BindingResult result,
            Model model
    ) throws IOException {
        if (result.hasErrors()) {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "add-edit-product";
        }
        productService.addProduct(product, product.getFile());
        return "redirect:/admin/products-manager";
    }

    /**
     * Handles submission of an edited product.
     *
     * @param id      the ID of the product to update
     * @param product the product entity with updated fields
     * @return redirect to product management on success
     * @throws IOException if file upload fails
     */
    @PostMapping("/add-product-page/edit/{id}")
    public String editProduct(
            @PathVariable("id") long id,
            @ModelAttribute Product product
    ) throws IOException {
        productService.editProduct(product, id);
        return "redirect:/admin/products-manager";
    }

    /**
     * Displays the category management page with all categories.
     *
     * @param model the model to populate view attributes
     * @return the view name for category management
     */
    @GetMapping("/categories")
    public String getAddCategoryPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories-manager";
    }

    /**
     * Displays the orders overview page with total orders, revenue, and order items.
     *
     * @param model the model to populate view attributes
     * @return the view name for the admin orders page
     */
    @GetMapping("/orders")
    public String getOrdersPage(Model model) {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        model.addAttribute("totalOrders", orderItems.size());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        model.addAttribute("orders", orderService.getTotalOrdersAmount());
        model.addAttribute("orderItems", orderItems);
        return "admin-orders-page";
    }
}
