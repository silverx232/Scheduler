package com.C195.model;

/**
 * Class for a Country.
 *
 * <p> This class defines a Country. A Country is associated with a Customer through a Division. </p>
 */
public class Country {
    private String countryName;
    private int countryID;

    /**
     * Constructor for Country.
     *
     * <p> This is the default constructor for the Country class. </p>
     */
    public Country() {
        super();
    }

    /**
     * Constructor for Country.
     *
     * <p> This is the constructor for the Country class. </p>
     * @param countryName The name of the country
     * @param countryID The ID of the country
     */
    public Country(String countryName, int countryID) {
        this.countryName = countryName;
        this.countryID = countryID;
    }

    /**
     * Getter for country name.
     *
     * <p> This is the getter for the name of the country. </p>
     * @return Returns the name of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter for country name.
     *
     * <p> This is the setter for the name of the country. </p>
     * @param countryName the name the country will have
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Getter for country ID.
     *
     * <p> This is the getter for the ID of the country. </p>
     * @return Returns the ID of the country
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Setter for country ID.
     *
     * <p> This is the setter for the ID of the country. </p>
     * @param countryID the ID the country will have
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * Override of toString().
     *
     * <p> This method overrides the toString() method and returns the Country's name. </p>
     * @return Returns the name of the country
     */
    @Override
    public String toString() {
        return countryName;
    }
}
