package com.C195.controller;

import com.C195.helper.DAOHelper;
import com.C195.helper.ErrorAlert;
import com.C195.helper.NavigationHelper;
import com.C195.model.Appointment;
import com.C195.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the screen that lets users view contacts.
 *
 * <p> This class is the controller for the GUI screen that displays contacts and their information. It allows the
 * user to select a contact and see that contact's appointments schedule. </p>
 */
public class ContactsInfo implements Initializable {
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Contact, Integer> contactIDColumn;
    @FXML
    private TableColumn<Contact, String> contactNameColumn;
    @FXML
    private TextField contactSearch;
    @FXML
    private TableView<Contact> contactTable;
    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Contact, String> emailColumn;
    @FXML
    private TableColumn<Appointment, String> endDateColumn;
    @FXML
    private TableView<Appointment> scheduleTable;
    @FXML
    private TableColumn<Appointment, String> startDateColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;

    private ObservableList<Contact> contactList;
    private ObservableList<Appointment> appointmentList;

    /**
     * The initializer for the controller.
     *
     * <p> This method initializes the controller class. It sets up the table views to show the contacts and a
     * contact's schedule. It sets up a listener on the contacts table to load that contact's appointments when the
     * contact is selected. </p>
     * <p> LAMBDA USE: A lambda is used here to reduce the amount of coding needed to set up the listener. The lambda
     * covers overwriting the code for a new ChangeListener. It takes an Observable value, the old value, and the new
     * changed value. It calls the setContactSchedule method on the new value. </p>
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactList = DAOHelper.getAllContacts();
        appointmentList = FXCollections.observableArrayList();

        contactTable.setItems(contactList);
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        scheduleTable.setItems(appointmentList);
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("stringStartTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("stringEndTime"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        contactTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> setContactSchedule(newValue));

        //Code the lambda is replacing
//        contactTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Contact> observableValue, Contact oldValue, Contact newValue) {
//                setContactSchedule(newValue);
//            }
//        });
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
     * Loads the customers information screen.
     *
     * <p> This method loads the CustomerInfo controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onViewCustomers(ActionEvent actionEvent) {
        NavigationHelper.viewCustomerInfo(this, actionEvent);
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

    /**
     * Searches the list of contacts using the given partial name or ID.
     *
     * <p> This method searches the list of contacts for those whose names contain the given String
     * or whose IDs match the given ID. It displays the entire list of contacts if an empty String is entered. </p>
     * @param actionEvent The event that called the method
     */
    public void onContactSearch(ActionEvent actionEvent) {
        String userSearch = contactSearch.getText().toLowerCase(Locale.ROOT);
        ObservableList<Contact> allList = DAOHelper.getAllContacts();
        contactList.clear();

        // Add all contacts whose name's contain userSearch. Will be all contacts if userSearch is ""
        for (Contact contact : allList) {
            if (contact.getName().toLowerCase(Locale.ROOT).contains(userSearch))
                contactList.add(contact);
        }

        // If no matches for name found, check ID
        if (contactList.size() == 0) {
            try {
                int userID = Integer.parseInt(userSearch);

                for (Contact contact: allList) {
                    if (contact.getContactID() == userID) {
                        contactList.add(contact);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
               // userSearch is not a valid integer
            }
        }

        if (contactList.size() == 0) {
            ErrorAlert.searchNotFoundError("contact");

            contactList = allList;
            contactTable.setItems(contactList);
            return;
        }

        if (contactList.size() == 1) {
            contactTable.getSelectionModel().select(0);
        }
    }

    /**
     * Shows the appointments associated with the given contact.
     *
     * <p> This method loads the table view with the appointments that are associated with the given Contact. It is
     * used by the Initialize method's listener on the contacts table. </p>
     * @param contact The user selected Contact
     */
    private void setContactSchedule(Contact contact) {
        if (contactTable.getSelectionModel().isEmpty()) {
            appointmentList.clear();
            return;
        }

        appointmentList = DAOHelper.getAppointmentsFromContact(contact.getContactID());
        scheduleTable.setItems(appointmentList);
    }
}
