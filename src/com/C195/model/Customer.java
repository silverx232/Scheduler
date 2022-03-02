package com.C195.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Class for a Customer.
 *
 * <p> This class defines a Customer. A Customer is associated with an Appointment and a Division. It contains
 * information on the Customer's ID, name, address, phone number, and who and when created and updated it. </p>
 */
public class Customer {
    private int customerID = -1;  // Will be -1 if customerID still needs to be auto-generated by the Database
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime creationDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    /**
     * Constructor for Customer.
     *
     * <p> This is the default constructor for the Customer class.</p>
     */
    public Customer() {
        super();
    }

    /**
     * Constructor for Customer.
     *
     * <p> This is the  constructor for the Customer class.</p>
     * @param name The name of the Customer
     * @param address The street address of the Customer
     * @param postalCode The postal code of the Customer's address
     * @param phone The phone number of the Customer
     * @param divisionID The Division's ID associated with the Customer's address
     */
    public Customer(String name, String address, String postalCode, String phone, int divisionID) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }

    /**
     * Getter for customer ID.
     *
     * <p> This is the getter for the ID of the customer. </p>
     * @return Returns the ID of the customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Setter for customer ID.
     *
     * <p> This is the setter for the ID of the customer. </p>
     * @param customerID the ID the customer will have
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for name.
     *
     * <p> This is the getter for the name of the customer. </p>
     * @return Returns the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * <p> This is the setter for the name of the customer. </p>
     * @param name the name the customer will have
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for address.
     *
     * <p> This is the getter for the address of the customer. </p>
     * @return Returns the address of the customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address.
     *
     * <p> This is the setter for the street address of the customer. </p>
     * @param address the street address the customer will have
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter for postal code.
     *
     * <p> This is the getter for the postal code of the customer's address. </p>
     * @return Returns the postal code of the customer's address
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter for postal code.
     *
     * <p> This is the setter for the postal code of the address of the customer. </p>
     * @param postalCode the postal code the customer will have
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter for creation date.
     *
     * <p> This is the getter for the creation date of the customer. </p>
     * @return Returns the creation date of the customer
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Setter for creation date.
     *
     * <p> This is the setter for the creation date of the customer. </p>
     * @param creationDate the creation date the customer will have
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Getter for customer's creator.
     *
     * <p> This is the getter for who created the customer. </p>
     * @return Returns the name of the customer's creator
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for customer's creator.
     *
     * <p> This is the setter for who created the customer. </p>
     * @param createdBy the name of the creator of the customer
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for update date.
     *
     * <p> This is the getter for the date of the last time the customer was updated. </p>
     * @return Returns the date of the last time the customer was updated
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for update date.
     *
     * <p> This is the setter for the date of the last time the customer was updated. </p>
     * @param lastUpdate the date of the last time the customer was updated
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for updater's name.
     *
     * <p> This is the getter for the name of the last person who updated the customer. </p>
     * @return Returns the name of the last person who updated the customer
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for updater's name.
     *
     * <p> This is the setter for the name of the last person who updated the customer. </p>
     * @param lastUpdatedBy the name of the last person who updated the customer
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for division ID.
     *
     * <p> This is the getter for the division ID of the customer. </p>
     * @return Returns the division ID of the customer
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for division ID.
     *
     * <p> This is the setter for the division ID of the customer. </p>
     * @param divisionID the division ID the customer will have
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Getter for phone number.
     *
     * <p> This is the getter for the phone number of the customer. </p>
     * @return Returns the phone number of the customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone number.
     *
     * <p> This is the setter for the phone number of the customer. </p>
     * @param phone the phone number the customer will have
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets a String of the creation date.
     *
     * <p> This method returns the creation date and time formatted into a user friendly String. </p>
     * @return Returns a String of the creation date and time
     */
    public String getStringCreationDate() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(creationDate);
    }

    /**
     * Gets a String of the last update date.
     *
     * <p> This method returns the last update date and time formatted into a user friendly String. </p>
     * @return Returns a String of the last update date and time
     */
    public String getStringLastUpdate() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(lastUpdate);
    }
}