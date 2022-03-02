package com.C195.controller;

import com.C195.helper.DAOHelper;
import com.C195.helper.ErrorAlert;
import com.C195.helper.NavigationHelper;
import com.C195.model.Country;
import com.C195.model.Customer;
import com.C195.model.Division;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the form that adds or updates a customer.
 *
 * <p> This class is the controller for the GUI form that allows the user to add or update a Customer. </p>
 */
public class CustomerAddUpdate implements Initializable {
    @FXML
    private Button addUpdateCustomer;
    @FXML
    private Label addUpdateLabel;
    @FXML
    private TextField addressTextfield;
    @FXML
    private Label customerIDLabel;
    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField numberTextfield;
    @FXML
    private TextField postalTextfield;
    @FXML
    private ComboBox<Country> selectCountryCombo;
    @FXML
    private ComboBox<Division> selectProvinceCombo;

    private boolean isUpdateCustomer = false;
    private Customer updateCustomer = null;
    private ObservableList<Country> countryList;
    private ObservableList<Division> divisionList;

    /**
     * The initializer for the controller.
     *
     * <p> This method initializes the controller class. It populates the combo box for selecting a Country.</p>
     * @param url The location of for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryList = DAOHelper.getAllCountries();
        selectCountryCombo.setItems(countryList);
    }

    /**
     * Passes the user selected Customer from the customer information screen to this controller.
     *
     * <p> This method is used by the CustomerInfo controller to pass the user selected Customer to this controller.
     * It fills in the fields in the form with the relevant information from the Customer. It also uses a boolean
     * to alert this controller that the user is modifying a customer instead of adding one. </p>
     * @param updateCustomer The Customer the user wants to modify
     */
    public void setUpdateCustomer(Customer updateCustomer) {
        this.updateCustomer = updateCustomer;
        isUpdateCustomer = true;

        // Change labels and buttons for updating a customer instead of adding
        addUpdateLabel.setText("Update Customer");
        addUpdateCustomer.setText("Update");

        customerIDLabel.setText(Integer.toString(updateCustomer.getCustomerID()));
        nameTextfield.setText(updateCustomer.getName());
        numberTextfield.setText(updateCustomer.getPhone());
        addressTextfield.setText(updateCustomer.getAddress());
        postalTextfield.setText(updateCustomer.getPostalCode());

        Division updateDivision = DAOHelper.getDivisionFromID(updateCustomer.getDivisionID());
        divisionList = DAOHelper.getAllDivisions(updateDivision);
        selectProvinceCombo.setItems(divisionList);

        for (Country country : countryList) {
            if (country.getCountryName().equals(updateDivision.getCountryName())) {
                selectCountryCombo.setValue(country);
                break;
            }
        }

        for (Division division : divisionList) {
            if (division.getDivisionID() == updateDivision.getDivisionID()) {
                selectProvinceCombo.setValue(division);
                break;
            }
        }
    }

    /**
     * Populates the division combo box with the appropriate Divisions for the selected Country.
     *
     * <p> This method uses the user selected Country to populate the division combo box with the appropriate
     * Divisions. </p>
     * @param actionEvent The event that called the method
     */
    public void onSelectCountry(ActionEvent actionEvent) {
        divisionList = DAOHelper.getAllDivisions(selectCountryCombo.getSelectionModel().getSelectedItem());
        selectProvinceCombo.setItems(divisionList);
    }

    /**
     * Creates or modifies a Customer and updates the database.
     *
     * <p> This method creates a new Customer or modifies the selected Customer using the information given by the
     * user. If the information doesn't pass the validation check, an alert is displayed with the relevant information.
     * If the information is valid, the database is updated. </p>
     * @param actionEvent The event that called the method
     */
    public void onAddUpdateCustomer(ActionEvent actionEvent) {
        String name = nameTextfield.getText();
        String phone = numberTextfield.getText();
        String address = addressTextfield.getText();
        String postalCode = postalTextfield.getText();
        Division division = selectProvinceCombo.getSelectionModel().getSelectedItem();

        // Validate form information
        String errorMessage = ErrorAlert.customerFormValidation(name, phone, address, postalCode, division);
        if (!errorMessage.isEmpty()) {
            ErrorAlert.invalidForm(errorMessage);
            return;
        }

        Customer customer = new Customer(name, address, postalCode, phone, division.getDivisionID());

        if (isUpdateCustomer) {
            customer.setCustomerID(updateCustomer.getCustomerID());
            DAOHelper.updateCustomer(customer);
        }
        else {
            DAOHelper.addCustomer(customer);
        }

        NavigationHelper.viewCustomerInfo(this, actionEvent);
    }

    /**
     * Returns the user to the customer information screen.
     *
     * <p> This method cancels the form without modifying the database and returns the user to view the customers. </p>
     * @param actionEvent The event that called the method
     */
    public void onCancel(ActionEvent actionEvent) {
        NavigationHelper.viewCustomerInfo(this, actionEvent);
    }
}
