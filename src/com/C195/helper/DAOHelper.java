package com.C195.helper;

import com.C195.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 *Class that contains methods to access the database.
 *
 * <p> This class contains methods that let the controllers access the database for information on Appointments,
 * Customers, Users, Contacts, Divisions, and Countries. </p>
 */
public abstract class DAOHelper {
    // Stores the user's name, for purposes of marking who changed the Database
    private static String userName = "";

    /**
     * Queries the database for all appointments.
     *
     * <p> This method queries the database to obtain a list of all Appointments stored in the database. </p>
     * @return Returns an ObservableList of all Appointments in the database
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointmentList= FXCollections.observableArrayList();
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Contact_ID, " +
                "Customer_ID, User_ID FROM appointments";

        try (PreparedStatement preparedStatement = (PreparedStatement) JDBC.connection.prepareStatement(sql);
              ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterate through resultSet and make a new Appointment for every row
            while (resultSet.next()) {
                Appointment appointment = new Appointment();

                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setLocation(resultSet.getString("Location"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setStartTime(resultSet.getTimestamp("Start").toLocalDateTime());
                appointment.setEndTime(resultSet.getTimestamp("End").toLocalDateTime());
                appointment.setContactID(resultSet.getInt("Contact_ID"));
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                appointment.setUserID(resultSet.getInt("User_ID"));

                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * Queries the database for all customers.
     *
     * <p> This method queries the database to obtain a list of all Customers stored in the database. </p>
     * @return Returns an ObservableList of all Customers in the database
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID FROM customers";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Iterate through resultSet and make a new Customer for every row
            while(resultSet.next()) {
                Customer customer = new Customer();

                customer.setCustomerID(resultSet.getInt("Customer_ID"));
                customer.setName(resultSet.getString("Customer_Name"));
                customer.setAddress(resultSet.getString("Address"));
                customer.setPostalCode(resultSet.getString("Postal_Code"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setCreationDate(resultSet.getTimestamp("Create_Date").toLocalDateTime());
                customer.setCreatedBy(resultSet.getString("Created_By"));
                customer.setLastUpdate(resultSet.getTimestamp("Last_Update").toLocalDateTime());
                customer.setLastUpdatedBy(resultSet.getString("Last_Updated_By"));
                customer.setDivisionID(resultSet.getInt("Division_ID"));

                customerList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return customerList;
    }

    /**
     *Adds an appointment to the database.
     *
     * <p> This method adds a given Appointment to the database. </p>
     * @param appointment The Appointment to be added to the database
     */
    public static void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = (PreparedStatement) JDBC.connection.prepareStatement(sql)) {

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, userName);
            preparedStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(10, userName);
            preparedStatement.setInt(11, appointment.getCustomerID());
            preparedStatement.setInt(12, appointment.getUserID());
            preparedStatement.setInt(13, appointment.getContactID());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Adds a customer to the database.
     *
     * <p> This method adds a given Customer to the database. </p>
     * @param customer The Customer to be added to the database
     */
    public static void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = (PreparedStatement) JDBC.connection.prepareStatement(sql)) {

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, userName);
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, userName);
            preparedStatement.setInt(9, customer.getDivisionID());

            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes an appointment from the database.
     *
     * <p> This method deletes a given Appointment from the database. </p>
     * @param appointmentID The ID of the Appointment that will be deleted
     */
    public static void deleteAppointment(int appointmentID) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, appointmentID);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Deletes a customer from the database.
     *
     * <p> This method deletes a given Customer from the database. A Customer's associated Appointments must be
     * deleted before the Customer can be deleted. </p>
     * @param customerID The ID of the Customer that will be deleted
     */
    public static void deleteCustomer(int customerID) {
        // Must delete all customer's appointments before deleting customer
        String sqlAppointment = "DELETE FROM appointments WHERE Customer_ID = " + customerID;

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sqlAppointment)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        String sql = "DELETE FROM customers WHERE Customer_ID = " + customerID;

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates an appointment in the database.
     *
     * <p> This method updates the information of an appointment in the database. </p>
     * @param appointment The updated Appointment. This appointment's ID will be used to match it in the database.
     */
    public static void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setString(4, appointment.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(appointment.getStartTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(appointment.getEndTime()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8, userName);
            preparedStatement.setInt(9, appointment.getCustomerID());
            preparedStatement.setInt(10, appointment.getUserID());
            preparedStatement.setInt(11, appointment.getContactID());
            preparedStatement.setInt(12, appointment.getAppointmentID());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Updates a customer in the database.
     *
     * <p> This method the information of a Customer in the database. </p>
     * @param customer The updated Customer. This customer's ID will be used to match it in the database.
     */
    public static void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql)) {

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, userName);
            preparedStatement.setInt(7, customer.getDivisionID());
            preparedStatement.setInt(8, customer.getCustomerID());

            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Queries the database for all countries.
     *
     * <p> This method queries the database to obtain a list of all Countries stored in the database. </p>
     * @return Returns an ObservableList of all Countries in the database
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM countries";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Country country = new Country();
                country.setCountryID(resultSet.getInt("Country_ID"));
                country.setCountryName(resultSet.getString("Country"));

                countryList.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryList;
    }

    /**
     * Qeuries the database for all divisions for a particular country.
     *
     * <p> This method queries the database to obtain a list of all Divisions stored in the database for a particular
     * Country. </p>
     * @param country The Country the Divisions should come from
     * @return Returns an ObservableList of all Divisions in the database for a particular country
     */
    public static ObservableList<Division> getAllDivisions(Country country) {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = " + country.getCountryID();

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Division division = new Division(country);
                division.setDivisionID(resultSet.getInt("Division_ID"));
                division.setDivisionName(resultSet.getString("Division"));

                divisionList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionList;
    }

    /**
     * Obtains a Division from the database using its ID.
     *
     * <p> This method uses a given Division ID to obtain information on the Division and its country. </p>
     * @param divisionID The ID of the Division to get information on
     * @return Returns a Division object containing its ID, name, country ID, and country name
     */
    public static Division getDivisionFromID(int divisionID) {
        String sql = "SELECT c.Country_ID, c.Country, f.Division FROM first_level_divisions AS f " +
                "INNER JOIN countries AS c ON f.Country_ID = c.Country_ID " +
                "WHERE f.Division_ID = " + divisionID;
        Division division = new Division();

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                division.setDivisionID(divisionID);
                division.setDivisionName(resultSet.getString("f.Division"));
                division.setCountryID(resultSet.getInt("c.Country_ID"));
                division.setCountryName(resultSet.getString("c.Country"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return division;
    }

    /**
     * Queries the database for all contacts.
     *
     * <p> This method queries the database to obtain a list of all Contacts stored in the database. </p>
     * @return Returns an ObservableList of all Contacts in the database
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setContactID(resultSet.getInt("Contact_ID"));
                contact.setName(resultSet.getString("Contact_Name"));
                contact.setEmail(resultSet.getString("Email"));

                contactList.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contactList;
    }

    /**
     * Obtains a contact from the database using its ID.
     *
     * <p> This method uses a given Contact ID to obtain the information in the database about it. </p>
     * @param contactID The ID of the Contact
     * @return Returns a Contact object containing its ID, name, and email
     */
    public static Contact getContactFromID (int contactID) {
        String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = " + contactID;
        Contact contact = new Contact();

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                contact.setContactID(contactID);
                contact.setName(resultSet.getString("Contact_Name"));
                contact.setEmail(resultSet.getString("Email"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contact;
    }

    /**
     * Obtains a list of appointments from a given contact ID.
     *
     * <p> This method uses a given Contact's ID to obtain a list of all Appointments for that contact. </p>
     * @param contactID The ID of the Contact
     * @return Returns an ObservableList of all Appointments associated with the given Contact ID
     */
    public static ObservableList<Appointment> getAppointmentsFromContact (int contactID) {
        String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments " +
                "WHERE Contact_ID = " + contactID;
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setStartTime(resultSet.getTimestamp("Start").toLocalDateTime());
                appointment.setEndTime((resultSet.getTimestamp("End").toLocalDateTime()));
                appointment.setCustomerID(resultSet.getInt("Customer_ID"));

                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * Obtains a list of appointments from a given customer ID.
     *
     * <p> This method uses a given Customer's ID to obtain a list of all Appointments for that Customer. </p>
     * @param customerID The ID of the Customer
     * @return Returns an ObservableList of all Appointments associated with the given Customer ID
     */
    public static ObservableList<Appointment> getAppointmentsFromCustomer (int customerID) {
        String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, Contact_ID FROM appointments " +
                "WHERE Customer_ID = " + customerID;
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(resultSet.getInt("Appointment_ID"));
                appointment.setTitle(resultSet.getString("Title"));
                appointment.setType(resultSet.getString("Type"));
                appointment.setDescription(resultSet.getString("Description"));
                appointment.setStartTime(resultSet.getTimestamp("Start").toLocalDateTime());
                appointment.setEndTime((resultSet.getTimestamp("End").toLocalDateTime()));
                appointment.setCustomerID(customerID);
                appointment.setContactID(resultSet.getInt("Contact_ID"));

                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * Queries the database to see if the given user name exists.
     *
     * <p> This method queries the database to see if the given name matches a User name. </p>
     * @param name The User name to be validated
     * @return Returns true if the name is a valid User name in the database
     */
    public static boolean validateUserName(String name) {
        String sql = "SELECT User_Name FROM users WHERE User_Name = '" + name + "'";
        boolean isValid = false;

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {  // If resultSet has any row in it, name is valid
                isValid = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isValid;
    }

    /**
     * Queries the database to see if the password matches the user name.
     *
     * <p> This method queries the database to see if the given password matches the password listed for the given
     * user's name. </p>
     * @param name The name of the User
     * @param password The password to be validated
     * @return Returns true if the given password matches the password listed for the user
     */
    public static boolean validateLogin(String name, String password) {
        String sql = "SELECT Password FROM users WHERE User_Name = '" + name + "'";
        boolean isValid = false;

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                if (password.equals(resultSet.getString("Password"))) {
                    isValid = true;
                    userName = name;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isValid;
    }

    /**
     * Queries the database to see if a customer exists.
     *
     * <p> The method queries the database to see if a given Customer's ID exists. </p>
     * @param customerID The ID of the customer
     * @return Returns true if the given Customer ID exists in the database
     */
    public static boolean validateCustomer (int customerID) {
        String sql = "SELECT Customer_ID FROM customers WHERE Customer_ID = " + customerID;
        boolean isValid = false;

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isValid;
    }

    /**
     * Queries the ddatabase to see if a user exists.
     *
     * <p> This method queries the database to see if a given User's ID exists. </p>
     * @param userID The ID of the user
     * @return Returns true if the given User ID exists in the database
     */
    public static boolean validateUser (int userID) {
        String sql = "SELECT User_ID FROM users WHERE User_ID = " + userID;
        boolean isValid = false;

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isValid;
    }

    /**
     * Obtains the amount of customers in the database per country.
     *
     * <p> This method counts up the amount of customers in the database, grouping them by country. </p>
     * @return Returns a HashMap with the countries as keys and the total amount of customers as values
     */
    public static HashMap<String, Integer> customersPerCountry() {
        String sql = "SELECT x.Country, COUNT(c.Customer_ID) AS Total FROM customers AS c INNER JOIN first_level_divisions AS d " +
                "ON c.Division_ID = d.Division_ID INNER JOIN countries AS x ON d.Country_ID = x.Country_ID " +
                "GROUP BY x.Country_ID";
        HashMap<String, Integer> map = new HashMap<>();

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                map.put(resultSet.getString("Country"), resultSet.getInt("Total"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return map;
    }

    /**
     * Obtains the total amount of appointments in the database per type for a given customer.
     *
     * <p> This method counts up the amount of appointments in the database for a given Customer ID, grouping them
     * by type. </p>
     * @param customerID The ID of the customer whose appointments will be counted
     * @return Returns a HashMap containing appointments for the given customer, with types as keys and the total amount of appointments as values.
     */
    public static HashMap<String, Integer> appointmentTypeTotal(int customerID) {
        HashMap<String, Integer> map = new HashMap<>();
        String sql = "SELECT a.Type, COUNT(a.Appointment_ID) AS Total FROM customers AS c INNER JOIN appointments AS a " +
                "ON c.Customer_ID = a.Customer_ID WHERE a.Customer_ID = " + customerID + " GROUP BY a.Type";

        try (PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                map.put(resultSet.getString("Type"), resultSet.getInt("Total"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return map;
    }
}
