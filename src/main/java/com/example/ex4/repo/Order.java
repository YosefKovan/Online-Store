package com.example.ex4.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer's order, including billing details, payment information, and ordered items.
 * <p>
 * Captures creation timestamp, shipping address, payment card details (transient), total amount,
 * and associations to the user account and individual order items.
 */
@Entity
@Table(name = "Orders")
public class Order {

    /**
     * Unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Timestamp when the order was created.
     */
    private LocalDateTime createdAt;

    /**
     * Shipping country; only letters and spaces allowed.
     */
    @NotBlank(message = "Country is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Country must contain only letters and spaces")
    private String country;

    /**
     * Name printed on the credit card.
     */
    @NotBlank(message = "Name on card is required")
    private String nameOnCard;

    /**
     * Shipping city; only letters and spaces allowed.
     */
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "City must contain only letters and spaces")
    private String city;

    /**
     * Shipping street address.
     */
    @NotBlank
    private String streetAddress;

    /**
     * Shipping ZIP code; only digits allowed.
     */
    @NotBlank
    @Pattern(regexp = "^[0-9]+$", message = "ZIP code must contain only digits")
    private String zipCode;

    /**
     * Transient credit card number for payment validation; not persisted.
     */
    @Transient

    @CreditCardNumber(message = "Invalid credit-card number")

    //@CreditCardNumber(message   = "Invalid credit-card number")

    private String creditCardNumber;

    /**
     * Transient card CCV; must be 3 or 4 digits.
     */
    @Transient
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CCV must be 3 or 4 digits")
    private String ccv;

    /**
     * Transient expiration date; must be a future YearMonth.
     */
    @Transient
    @NotNull(message = "Expiration date is required")
    @Future(message = "Expiration date must be in the future")
    private YearMonth expirationDate;

    /**
     * Total payment amount; must be positive (minimum value enforced).
     */
    @DecimalMin("0.000001")
    private double totalPayment;

    /**
     * The user account that placed this order.
     */
    @ManyToOne
    private UserAccount userAccount;

    /**
     * List of items included in this order.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    /**
     * Default constructor initializing creation time and order items list.
     */
    public Order() {
        this.createdAt = LocalDateTime.now();
        this.orderItems = new ArrayList<>();
    }

    /**
     * Returns the order ID.
     *
     * @return the unique order ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the order ID.
     *
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the creation timestamp.
     *
     * @return creation time of the order
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt the timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the shipping country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the shipping country.
     *
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns the name on the card.
     *
     * @return the cardholder name
     */
    public String getNameOnCard() {
        return nameOnCard;
    }

    /**
     * Sets the cardholder name.
     *
     * @param nameOnCard the name to set
     */
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    /**
     * Returns the shipping city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the shipping city.
     *
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the street address.
     *
     * @return the street address
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the street address.
     *
     * @param streetAddress the address to set
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Returns the ZIP code.
     *
     * @return the ZIP code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the ZIP code.
     *
     * @param zipCode the ZIP code to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Returns the credit card number (transient).
     *
     * @return the credit card number
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Sets the credit card number (transient).
     *
     * @param creditCardNumber the number to set
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * Returns the CCV code (transient).
     *
     * @return the CCV
     */
    public String getCcv() {
        return ccv;
    }

    /**
     * Sets the CCV code (transient).
     *
     * @param ccv the CCV to set
     */
    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    /**
     * Returns the card expiration date (transient).
     *
     * @return expiration date as YearMonth
     */
    public YearMonth getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the card expiration date (transient).
     *
     * @param expirationDate the date to set
     */
    public void setExpirationDate(YearMonth expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Returns the total payment amount.
     *
     * @return the total payment
     */
    public double getTotalPayment() {
        return totalPayment;
    }

    /**
     * Sets the total payment amount.
     *
     * @param totalPayment the amount to set
     */
    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    /**
     * Returns the user account that placed this order.
     *
     * @return the UserAccount
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * Sets the user account that placed this order.
     *
     * @param userAccount the UserAccount to set
     */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * Returns the list of order items.
     *
     * @return list of OrderItem
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets the list of order items, replacing any existing items.
     *
     * @param orderItems list to set
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Adds a single OrderItem to this order.
     *
     * @param orderItem the OrderItem to add
     */
    public void setOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }
}
