package com.C195.controller;

import com.C195.helper.DAOHelper;
import com.C195.helper.ErrorAlert;
import com.C195.helper.NavigationHelper;
import com.C195.model.Appointment;
import com.C195.model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Controller for the screen that displays reports about the customers.
 *
 * <p> This class is the controller for the GUI screen that displays reports about the customers. It shows a bar graph
 * of the total amount of customers per country. It also lets the user select a customer to see that customer's total
 * amount of appointments per type and current month. </p>
 */
public class ReportsScreen implements Initializable {
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    private Label customerNameLabel;
    @FXML
    private TextField customerSearch;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private Label monthLabel;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private Label typeLabel;

    ObservableList<Customer> customerList;

    /**
     * The initializer for the controller.
     *
     * <p> This method initializes the controller class. It sets up the table view to show the customers. It adds a
     * listener to that customer table. It also loads the bar chart graph with the information from the database. </p>
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the country bar chart
        HashMap<String, Integer> countryCustomer = DAOHelper.customersPerCountry();
        XYChart.Series series = new XYChart.Series();
        series.setName("Countries");

        for (Map.Entry<String, Integer> current : countryCustomer.entrySet()) {
            series.getData().add(new XYChart.Data<>(current.getKey(), current.getValue()));
        }

        barChart.getData().addAll(series);

        // Initialize the Customer Table
        customerList = DAOHelper.getAllCustomers();
        customersTable.setItems(customerList);

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // A selection on the customer table sets the Total Appointment labels
        customersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setTotalAppointments(newValue));
    }

    /**
     * Searches the list of customers using the given partial name or ID.
     *
     * <p> This method searches the list of customers for those whose names contain the given String
     * or whose IDs match the given ID. It displays the entire list of customers if an empty String is entered. </p>
     * @param actionEvent The event that called the method
     */
    public void onCustomerSearch(ActionEvent actionEvent) {
        String userSearch = customerSearch.getText().toLowerCase(Locale.ROOT);
        ObservableList<Customer> allList = DAOHelper.getAllCustomers();
        customerList.clear();

        // Add all contacts whose name's contain userSearch. Will be all contacts if userSearch is ""
        for (Customer customer : allList) {
            if (customer.getName().toLowerCase(Locale.ROOT).contains(userSearch))
                customerList.add(customer);
        }

        // If no matches for name found, check ID
        if (customerList.size() == 0) {
            try {
                int userID = Integer.parseInt(userSearch);

                for (Customer customer: allList) {
                    if (customer.getCustomerID() == userID) {
                        customerList.add(customer);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // userSearch is not a valid integer
            }
        }

        if (customerList.size() == 0) {
            ErrorAlert.searchNotFoundError("customer");

            customerList = allList;
            customersTable.setItems(customerList);
            return;
        }

        if (customerList.size() == 1) {
            customersTable.getSelectionModel().select(0);
        }
    }

    /**
     * Loads the contacts information screen.
     *
     * <p> This method loads the ContactsInfo controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onViewContacts(ActionEvent actionEvent) {
        NavigationHelper.viewContactsInfo(this, actionEvent);
    }

    /**
     * Loads the customers information screen.
     *
     * <p> This method loads the CustomerInfo controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onViewCustomers(ActionEvent actionEvent) {
        NavigationHelper.viewCustomerInfo(this, actionEvent);
    }

    /**
     * Lets the user view all the appointments in the database.
     *
     * <p> This method loads the AppointmentsInfo controller, letting the user view all appointments. </p>
     * @param actionEvent The event that called the method
     */
    public void onViewAllAppointments(ActionEvent actionEvent) {
        NavigationHelper.viewAppointmentsInfo(this, actionEvent);
    }

    /**
     * Closes the application.
     *
     * <p> This method exits the program. </p>
     * @param actionEvent The event that called the method
     */
    public void onExit(ActionEvent actionEvent) {
        NavigationHelper.exit(actionEvent);
    }

    /**
     * Displays information about the total number of appointments for a customer.
     *
     * <p> This method is displays information about the selected customer's appointments. It shows the total amount of
     * appointments per current month and per type of appointment. It is called by the listener on the customer table
     * from the initialize() method. </p>
     * @param customer The user selected customer
     */
    private void setTotalAppointments(Customer customer) {
        if (customersTable.getSelectionModel().isEmpty()) {
            customerNameLabel.setText("Please select a customer.");
            monthLabel.setText("");
            typeLabel.setText("");
            return;
        }

        customerNameLabel.setText(customer.getName());
        HashMap<String, Integer> typeTotal = DAOHelper.appointmentTypeTotal(customer.getCustomerID());
        StringBuilder typeString = new StringBuilder();

        for (Map.Entry<String, Integer> current : typeTotal.entrySet()) {
            typeString.append(current.getKey() + " (" + current.getValue() + ")   ");
        }
        typeLabel.setText(typeString.toString());

        int count = 0;
        ObservableList<Appointment> allList = DAOHelper.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();

        for (Appointment current : allList) {
            if (current.getStartTime().getMonth().equals(now.getMonth()) ||
                    current.getEndTime().getMonth().equals(now.getMonth())) {
                if (current.getCustomerID() == customer.getCustomerID())
                    count++;
            }
        }
        monthLabel.setText(Integer.toString(count));
    }
}
