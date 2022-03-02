package com.C195.controller;

import com.C195.helper.DAOHelper;
import com.C195.helper.ErrorAlert;
import com.C195.helper.NavigationHelper;
import com.C195.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the screen that lets users view customers.
 *
 * <p> This class is the controller for the GUI screen that displays customers and their information. It allows
 * the user to search, add, modify, or delete those customers. </p>
 */
public class CustomerInfo implements Initializable {
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> createdByColumn;
    @FXML
    private TableColumn<Customer, String> creationDateColumn;
    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    private TextField customerSearch;
    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> divisionIDColumn;
    @FXML
    private TableColumn<Customer, String> lastUpdateColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> updatedByColumn;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    /**
     * The initializer for the controller.
     *
     * <p> This method initializes the controller class. It sets up the table view to show the customers. </p>
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerList = DAOHelper.getAllCustomers();

        // Set table view
        customersTable.setItems(customerList);

        // Bind arguments to columns
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        updatedByColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("stringCreationDate"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("stringLastUpdate"));
    }

    /**
     * Loads the form to add a customer.
     *
     * <p> This method loads the form that allows the user to add a customer. </p>
     * @param actionEvent The event that called the method
     */
    public void onAddCustomer(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/C195/view/CustomerAddUpdate.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Add Customer");
        stage.show();
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
     * Loads the form to update a customer.
     *
     * <p> This method loads the form that allows the user to modify an existing customer. It passes the selected
     * Customer into the CustomerAddUpdate controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onUpdateCustomer(ActionEvent actionEvent) {
        if (customersTable.getSelectionModel().isEmpty()) {  // No items were selected
            ErrorAlert.selectionError("a customer");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/C195/view/CustomerAddUpdate.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomerAddUpdate customerAddUpdate = loader.getController();
        Customer customer = customersTable.getSelectionModel().getSelectedItem();
        customerAddUpdate.setUpdateCustomer(customer);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Update Customer");
        stage.show();
    }

    /**
     * Deletes the selected customer.
     *
     * <p> This method deletes the selected customer from the database. It displays an alert asking for confirmation
     * from the user and informing the user that deleting a customer also deletes all associated appointments. </p>
     * @param actionEvent The event that called the method
     */
    public void onDeleteCustomer(ActionEvent actionEvent) {
        if (customersTable.getSelectionModel().isEmpty()) {
            ErrorAlert.selectionError("a customer");
            return;
        }

        boolean isConfirmed = ErrorAlert.deleteCustomerConfirmation();
        if (!isConfirmed)
            return;

        Customer customer = customersTable.getSelectionModel().getSelectedItem();
        DAOHelper.deleteCustomer(customer.getCustomerID());
        customerList.remove(customer);
    }

    /**
     * Lets the user view a customer's appointments.
     *
     * <p> This method shows the selected customer's associated appointments. It loads the AppointmentsInfo controller
     * and passes that controller the selected Customer. </p>
     * @param actionEvent The event that called the method
     */
    public void onCustomerAppointments(ActionEvent actionEvent) {
        if (customersTable.getSelectionModel().isEmpty()) {
            ErrorAlert.selectionError("a customer");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/C195/view/AppointmentsInfo.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppointmentsInfo appointmentsInfo = loader.getController();
        Customer customer = customersTable.getSelectionModel().getSelectedItem();
        appointmentsInfo.setAppointmentList(customer);
        appointmentsInfo.setAllAppointmentsButton(true);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Customer Appointments");
        stage.show();
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
     * Lets the user view all the appointments in the database.
     *
     * <p> This method loads the AppointmentsInfo controller, letting the user view all appointments. </p>
     * @param actionEvent The event that called the method
     */
    public void onViewAllAppointments(ActionEvent actionEvent) {
        NavigationHelper.viewAppointmentsInfo(this, actionEvent);
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
     * Loads the reports information screen.
     *
     * <p> This method loads the ReportsScreen controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onReports(ActionEvent actionEvent) {
        NavigationHelper.viewReportsScreen(this, actionEvent);
    }

}
