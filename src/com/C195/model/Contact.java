package com.C195.model;

/**
 * Class for a Contact.
 *
 * <p> This class defines a Contact. A Contact is associated with an Appointment. </p>
 */
public class Contact {
    private int contactID;
    private String name;
    private String email;

    /**
     * Constructor for Contact.
     *
     * <p> This is the default constructor for the Contact class. </p>
     */
    public Contact() {
        super();
    }

    /**
     * Constructor for Contact.
     *
     * <p> This is the constructor for the Contact class. </p>
     * @param contactID The ID of the contact
     * @param name The name of the contact
     * @param email The email of the contact
     */
    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    /**
     * Getter for ID.
     *
     * <p> This is the getter for the ID of the contact. </p>
     * @return Returns the ID of the contact
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Setter for ID.
     *
     * <p> This is the setter for the ID of the contact. </p>
     * @param contactID the ID the contact will have
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Getter for name.
     *
     * <p> This is the getter for the name of the contact. </p>
     * @return Returns the name of the contact
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * <p> This is the setter for the name of the contact. </p>
     * @param name the name the contact will have
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for email.
     *
     * <p> This is the getter for the email of the contact. </p>
     * @return Returns the email of the contact
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email.
     *
     * <p> This is the setter for the email of the contact. </p>
     * @param email the email the contact will have
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Override of toString().
     *
     * <p> This method overrides the toString() method and returns the Contact's name. </p>
     * @return Returns the name of the contact
     */
    @Override
    public String toString() {
        return name;
    }

}
