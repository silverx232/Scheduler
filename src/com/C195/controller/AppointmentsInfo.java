package com.C195.controller;

import com.C195.helper.DAOHelper;
import com.C195.helper.ErrorAlert;
import com.C195.helper.NavigationHelper;
import com.C195.model.Appointment;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the screen that lets users view appointments.
 *
 * <p> This class is the controller for the GUI screen that displays appointments and their information. It allows the
 * user to search, add, modify, or delete those appointments. </p>
 */
public class AppointmentsInfo implements Initializable {
    @FXML
    private RadioButton allAppointmentsRadio;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    private TextField appointmentSearch;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, Integer> contactColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> endDateColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableColumn<Appointment, String> startDateColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, Integer> userIDColumn;
    @FXML
    private Button viewAllAppointmentsButton;

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * The initializer for the controller.
     *
     * <p> This method initializes the controller class. It sets up the table view to show the appointments. </p>
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add Appointments from database to appointments list
        appointments = DAOHelper.getAllAppointments();

        //Set table view
        appointmentsTable.setItems(appointments);

        // Bind arguments to columns
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("stringStartTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("stringEndTime"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * Searches the list of appointments using the given partial title or ID.
     *
     * <p> This method searches the list of appointments for those whose titles contain the given String
     * or whose IDs match the given ID. It displays the entire list of appointments if an empty String is entered. </p>
     * @param actionEvent The event that called the method
     */
    public void onAppointmentSearch(ActionEvent actionEvent) {
        allAppointmentsRadio.setSelected(true);
        String userSearch = appointmentSearch.getText().toLowerCase(Locale.ROOT);
        ObservableList<Appointment> allList = DAOHelper.getAllAppointments();
        appointments.clear();

        // Add all contacts whose name's contain userSearch. Will be all contacts if userSearch is ""
        for (Appointment appointment : allList) {
            if (appointment.getTitle().toLowerCase(Locale.ROOT).contains(userSearch))
                appointments.add(appointment);
        }

        // If no matches for name found, check ID
        if (appointments.size() == 0) {
            try {
                int userID = Integer.parseInt(userSearch);

                for (Appointment appointment: allList) {
                    if (appointment.getAppointmentID() == userID) {
                        appointments.add(appointment);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // userSearch is not a valid integer
            }
        }

        if (appointments.size() == 0) {
            ErrorAlert.searchNotFoundError("appointment");

            appointments = allList;
            appointmentsTable.setItems(appointments);
            return;
        }

        if (appointments.size() == 1) {
            appointmentsTable.getSelectionModel().select(0);
        }
    }

    /**
     * Loads the form to update an appointment.
     *
     * <p> This method loads the form that allows the user to modify an existing appointment. It passes the selected
     * Appointment into the AppointmentAddUpdate controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onUpdateAppointment(ActionEvent actionEvent) {
        if (appointmentsTable.getSelectionModel().isEmpty()) {
            ErrorAlert.selectionError("an appointment");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/C195/view/AppointmentAddUpdate.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppointmentAddUpdate appointmentAddUpdate = loader.getController();
        Appointment appointment = appointmentsTable.getSelectionModel().getSelectedItem();
        appointmentAddUpdate.setUpdateAppointment(appointment);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Update Appointment");
        stage.show();
    }

    /**
     * Deletes the selected appointment.
     *
     * <p> This method deletes the selected appointment from the database. It displays an alert asking for confirmation
     * and an alert informing the user of the successfully deleted appointment. </p>
     * @param actionEvent The event that called the method
     */
    public void onDeleteAppointment(ActionEvent actionEvent) {
        if (appointmentsTable.getSelectionModel().isEmpty()) {
            ErrorAlert.selectionError("an appointment");
            return;
        }

        boolean isConfirmed = ErrorAlert.deleteConfirmation("appointment");
        if (!isConfirmed)
            return;

        Appointment selected = appointmentsTable.getSelectionModel().getSelectedItem();
        DAOHelper.deleteAppointment(selected.getAppointmentID());
        appointments.remove(selected);
        ErrorAlert.appointmentCancelled(selected);
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
     * Loads the customers information screen.
     *
     * <p> This method loads the CustomerInfo controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onViewCustomers(ActionEvent actionEvent) {
        NavigationHelper.viewCustomerInfo(this, actionEvent);
    }

    /**
     * Shows all appointments in the database.
     *
     * <p> This method sets the table view to show all appointments that exist in the database. </p>
     * @param actionEvent The event that called the method
     */
    public void onAllAppointmentsRadio(ActionEvent actionEvent) {
        appointmentSearch.setText("");
        ObservableList<Appointment> allList = DAOHelper.getAllAppointments();
        appointments.clear();
        appointments.addAll(allList);
    }

    /**
     * Filters the appointments shown to those that occur in the current month.
     *
     * <p> This method shows only the appointments that occur in the current month. </p>
     * @param actionEvent The event that called the method
     */
    public void onCurrentMonthRadio(ActionEvent actionEvent) {
        appointmentSearch.setText("");
        ObservableList<Appointment> allList = DAOHelper.getAllAppointments();
        appointments.clear();
        LocalDateTime now = LocalDateTime.now();

        for (Appointment current : allList) {
            if (current.getStartTime().getMonth().equals(now.getMonth()) ||
                    current.getEndTime().getMonth().equals(now.getMonth()))
                appointments.add(current);
        }
    }

    /**
     * Filters the appointments shown to those that occur in the next week.
     *
     * <p> This methods shows only the appointments that occur in the next week, starting from now. </p>
     * @param actionEvent The event that called the method
     */
    public void onCurrentWeekRadio(ActionEvent actionEvent) {
        appointmentSearch.setText("");
        ObservableList<Appointment> allList = DAOHelper.getAllAppointments();
        appointments.clear();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfWeek = now.plusWeeks(1);

        for (Appointment current : allList) {
            LocalDateTime startTime = current.getStartTime();
            LocalDateTime endTime = current.getEndTime();
            if ( (startTime.isAfter(now) && startTime.isBefore(endOfWeek)) ||
                    (endTime.isAfter(now) && endTime.isBefore(endOfWeek)) )
                appointments.add(current);
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
     * Loads the reports information screen.
     *
     * <p> This method loads the ReportsScreen controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onReports(ActionEvent actionEvent) {
        NavigationHelper.viewReportsScreen(this, actionEvent);
    }

    /**
     * Loads the form to add an appointment.
     *
     * <p> This method loads the form that allows the user to add an appointment. </p>
     * @param actionEvent The event that called the method
     */
    public void onAddAppointment(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/C195/view/AppointmentAddUpdate.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Add Appointment");
        stage.show();
    }

    /**
     * Shows only the appointments for a specific customer.
     *
     * <p> This method sets the table view to show only the appointments for the given customer. </p>
     * <p> LAMBDA USE: A Lambda is used here to make the code for filtering the list of appointments easier to read.
     * It turns the list of all appointments into a stream, and then filters that stream using the lambda. It takes a
     * Customer object and then tells the filter to select those customers whose ID matches the selectedCustomer ID. </p>
     * @param selectedCustomer The customer whose appointments will be shown
     */
    public void setAppointmentList(Customer selectedCustomer) {
        List<Appointment> filteredList = appointments.stream().filter(
                customer -> customer.getCustomerID() == selectedCustomer.getCustomerID())
                .collect(Collectors.toList());

        appointments.clear();
        appointments.addAll(filteredList);
    }

    /**
     * Lets the user view all the appointments in the database.
     *
     * <p> This method loads the AppointmentsInfo controller, letting the user view all appointments. The button that
     * calls this method is only visible if the appointments for a specific customer were loaded into this
     * controller. </p>
     * @param actionEvent The event that called the method
     */
    public void onViewAllAppointments(ActionEvent actionEvent) {
        NavigationHelper.viewAppointmentsInfo(this, actionEvent);
    }

    /**
     * Sets the View All Appointments button to visible.
     *
     * <p> This method sets the View All Appointments button to visible. This button should be visible if the only
     * the appointments for a specific customer were loaded into this controller. </p>
     * @param value The event that called the method
     */
    public void setAllAppointmentsButton(boolean value) {
        viewAllAppointmentsButton.setVisible(value);
    }
}
