Names:
   Yosef Kovan - yosefko@edu.jmc.ac.il
   Shamuel Friedman - shamuelfr@edu.jmc.ac.il

  Amazon-Style E-Commerce Web Application

Admin email : admin@gmail.com
Admin Password : Test@2025
  
  Overview

This project is a Java & Spring Boot web application inspired by Amazon’s core features. It lets users browse products by category, add items to a persistent shopping cart, place orders, and submit ratings and reviews. Administrators can manage the product catalog and categories through a simple panel.

  Technology Stack

   Language & Framework: Java 17 with Spring Boot (Spring MVC, Spring Data JPA, Hibernate, Jakarta Validation)
   Database: MySQL Template Engine: Thymeleaf with Bootstrap 5 for responsive, mobile-friendly pages

   What You’ll Find in the Project

1.  Domain Models & Repositories

     Entities for products, categories, users, cart items and reviews
     Spring Data JPA repositories to handle persistence and queries

2.  Services & Business Logic

     Services that encapsulate operations such as adding to cart, checking out, creating reviews, and enforcing business rules

3.  Web Layer

     RESTful controllers for all key operations: listing products, viewing details, managing the session cart, submitting and fetching reviews
     Thymeleaf templates that render dynamic HTML pages, including reusable fragments for navigation and footers

4.  Session-Scoped Shopping Cart

     A dedicated Spring bean holds the current user’s cart in memory
     On checkout, those items become part of the permanent order history

5.   Validation Rules

     Passwords must be at least 8 characters long and include at least one letter
     Review ratings are enforced to be whole numbers between 1 and 5

   Database Schema (Conceptually)

Category: unique ID and name
Product: ID, name, description, price, and link to its category
UserAccount: ID, username, encrypted password, and roles (e.g. ROLE\_USER, ROLE\_ADMIN)
CartItem: links a user to products and tracks quantity during a session
Review: numeric rating, optional comment, timestamp, and references to both product and user

   How It Works

Browsing & Searching**: Visitors can filter products by category, view details and real-time average ratings.
Shopping Cart: Adding or removing items happens instantly in the session cart; the UI always reflects current quantities.
Checkout & Order History: Once confirmed, cart contents are saved to a simple order history table so past carts can be reviewed.
Reviews & Ratings: Registered users can leave a star rating (1–5) and an optional text comment. All reviews display in chronological order on the product page.
Admin Panel: Administrators can add, edit or remove categories and products. If a product exists in any active cart, deletion is blocked with a clear error.

   Sample Data for Demo

For a better user experience, you can import the provided SQL dump to pre-load the database with sample products, users and reviews. This lets you explore the full functionality—catalog browsing, order history and review displays—without manually entering data.

