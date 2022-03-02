package com.C195.model;

/**
 * Class for a Division.
 *
 * <p> This class defines a Division. A Division is a division, province, or state of a Country. A Division is
 * associated with a Customer through its ID. </p>
 */
public class Division extends Country {
    private String divisionName;
    private int divisionID;

    /**
     * Constructor for Division.
     *
     * <p> This is the default constructor for the Division class. </p>
     */
    public Division() {
        super();
    }

    /**
     * Constructor for Division.
     *
     * <p> This is the constructor for the Division class. </p>
     * @param countryName The name of the Country
     * @param countryID The ID of the Country
     * @param divisionName the name of the Division
     * @param divisionID the ID of the Division
     */
    public Division(String countryName, int countryID, String divisionName, int divisionID) {
        super(countryName, countryID);
        this.divisionName = divisionName;
        this.divisionID = divisionID;
    }

    /**
     * Constructor for Division.
     *
     * <p> This is the constructor for the Division class. </p>
     * @param country The Country object associated with the Division
     */
    public Division(Country country) {
        super(country.getCountryName(), country.getCountryID());
        this.divisionName = null;
        this.divisionID = -1;
    }

    /**
     * Getter for division name.
     *
     * <p> This is the getter for the name of the division. </p>
     * @return Returns the name of the division
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Setter for division name.
     *
     * <p> This is the setter for the name of the division. </p>
     * @param divisionName the name the division will have
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Getter for division ID.
     *
     * <p> This is the getter for the ID of the division. </p>
     * @return Returns the ID of the division
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * Setter for division ID.
     *
     * <p> This is the setter for the ID of the division. </p>
     * @param divisionID the ID the division will have
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * Override of toString().
     *
     * <p> This method overrides the toString() method and returns the Division's name. </p>
     * @return Returns the name of the division
     */
    @Override
    public String toString() {
        return divisionName;
    }
}
