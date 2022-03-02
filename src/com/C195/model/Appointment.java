package com.C195.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Class for an appointment.
 *
 * <p> This class defines an Appointment. It has an associated Customer, Contact, and user. It contains information on
 * the ID, title, description, type, location, and start and end times. </p>
 */
public class Appointment {
    /**
     * Zone where the business hours are located in: America/New_York
     */
    public static final ZoneId BUSINESS_ZONE = ZoneId.of("America/New_York");

    /**
     * Start of business hours
     */
    public static final ZonedDateTime BUSINESS_HOURS_START = ZonedDateTime.of(2000,1, 1, 8,
            0, 0, 0, BUSINESS_ZONE);

    /**
     * End of business hours
     */
    public static final ZonedDateTime BUSINESS_HOURS_END = ZonedDateTime.of(2000, 1, 1, 17,
            0, 0, 0, BUSINESS_ZONE);  // end of business hours

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor for Appointment.
     *
     * <p> This is the default constructor for the Appointment class. An appointment ID is auto-generated by the
     * database </p>
     */
    public Appointment() {
        super();
        this.appointmentID = -1;  // appointmentID should be set by the database
    }

    /**
     * Constructor for Appointment.
     *
     * <p> This is the constructor for the Appointment class. An appointment ID is auto-generated by the
     * database </p>
     * @param appointmentID The ID of the appointment
     * @param title The title of the appointment
     * @param description A description of the appointment
     * @param location The location of the appointment
     * @param type The type of appointment
     * @param startTime The start date and time of the appointment
     * @param endTime The end date and time of the appointment
     * @param customerID The ID of the Customer associated with the appointment
     * @param userID The ID of the user associated with the appointment
     * @param contactID The ID of the Contact associated with the appointment
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime startTime, LocalDateTime endTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Getter for ID.
     *
     * <p> This is the getter for the ID of the appointment. </p>
     * @return The ID of the appointment
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter for ID.
     *
     * <p> This is the setter for the ID of the appointment. </p>
     * @param appointmentID the ID the appointment will have
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter for title.
     *
     * <p> This is the getter for the title of the appointment. </p>
     * @return The title of the appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title.
     *
     * <p> This is the setter for the title of the appointment. </p>
     * @param title the title the appointment will have
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for description.
     *
     * <p> This is the getter for the description of the appointment. </p>
     * @return The description of the appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description.
     *
     * <p> This is the setter for the description of the appointment. </p>
     * @param description the description the appointment will have
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for location.
     *
     * <p> This is the getter for the location of the appointment. </p>
     * @return The location of the appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter for location.
     *
     * <p> This is the setter for the location of the appointment. </p>
     * @param location the location the appointment will have
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for type.
     *
     * <p> This is the getter for the type of the appointment. </p>
     * @return The type of the appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for type.
     *
     * <p> This is the setter for the type of the appointment. </p>
     * @param type the type the appointment will have
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for start date and time.
     *
     * <p> This is the getter for the start date and time of the appointment. </p>
     * @return The LocalDateTime of the start of the appointment
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Setter for start date and time.
     *
     * <p> This is the setter for the start date and time of the appointment. </p>
     * @param startTime the start date and time the appointment will have
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for end date and time.
     *
     * <p> This is the getter for the end date and time of the appointment. </p>
     * @return The LocalDateTime of the end of the appointment
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Setter for end date and time.
     *
     * <p> This is the setter for the end date and time of the appointment. </p>
     * @param endTime the end date and time the appointment will have
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Getter for customer ID.
     *
     * <p> This is the getter for the Customer's ID of the appointment. </p>
     * @return The customer's ID of the appointment
     */
    public int getCustomerID() {
        return customerID;
    }


    /**
     * Setter for customer ID.
     *
     * <p> This is the setter for the Customer's ID of the appointment. </p>
     * @param customerID the Customer's ID the appointment will have
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter for user ID.
     *
     * <p> This is the getter for the user's ID of the appointment. </p>
     * @return The ID of the user associated with the appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Setter for user ID.
     *
     * <p> This is the setter for the user's ID of the appointment. </p>
     * @param userID the ID of the user associated with the appointment
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Getter for contact ID.
     *
     * <p> This is the getter for the Contact's ID of the appointment. </p>
     * @return The Contact's ID of the appointment
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for contact ID.
     *
     * <p> This is the setter for the Contact's ID of the appointment. </p>
     * @param contactID the Contact's ID the appointment will have
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets a String of the start time.
     *
     * <p> This method returns the start date and time formatted into a user friendly String. </p>
     * @return Returns a String of the start date and time
     */
    public String getStringEndTime() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(endTime);
    }

    /**
     * Gets a String of the end time.
     *
     * <p> This method returns the end date and time formatted into a user friendly String. </p>
     * @return Returns a String of the end date and time
     */
    public String getStringStartTime() {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(startTime);
    }
}