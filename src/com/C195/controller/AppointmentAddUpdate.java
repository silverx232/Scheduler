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
import javafx.scene.control.*;

import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

/**
 * Controller for the form that adds or updates an appointment.
 *
 * <p> This class is the controller for the GUI form that allows the user to add or update an Appointment. </p>
 */
public class AppointmentAddUpdate implements Initializable {
    @FXML
    private Button addUpdateAppointment;
    @FXML
    private Label addUpdateLabel;
    @FXML
    private Label appointmentIDLabel;
    @FXML
    private TextField customerTextfield;
    @FXML
    private TextField descriptionTextfield;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField locationTextfield;
    @FXML
    private ComboBox<Contact> selectContactCombo;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private ComboBox<Integer> startHoursCombo;
    @FXML
    private ComboBox<Integer> startMinutesCombo;
    @FXML
    private Label startTimeDisplay;
    @FXML
    private ComboBox<Integer> endHoursCombo;
    @FXML
    private ComboBox<Integer> endMinutesCombo;
    @FXML
    private Label endTimeDisplay;
    @FXML
    private TextField titleTextfield;
    @FXML
    private TextField typeTextfield;
    @FXML
    private TextField userTextfield;

    private Appointment updateAppointment;
    private boolean isUpdate = false;
    private ObservableList<Contact> contactList;

    /**
     * The initializer for the controller.
     *
     * <p> This method initializes the controller class. It populates the combo boxes for selecting contacts and a time
     * within the business hours of the organization. Business hours are found in the Appointment class. </p>
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> startHoursList;
        ObservableList<Integer> startMinutesList;
        ObservableList<Integer> endHoursList;
        ObservableList<Integer> endMinutesList;
        contactList = DAOHelper.getAllContacts();
        selectContactCombo.setItems(contactList);

        //Populate the time selectors
        startHoursList = FXCollections.observableArrayList();
        startMinutesList = FXCollections.observableArrayList();
        endHoursList = FXCollections.observableArrayList();
        endMinutesList = FXCollections.observableArrayList();
        ZonedDateTime businessStart = Appointment.BUSINESS_HOURS_START.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime businessEnd = Appointment.BUSINESS_HOURS_END.withZoneSameInstant(ZoneId.systemDefault());

        //Populate Hours
        while (businessStart.compareTo(businessEnd) < 0) {
            int hours = businessStart.toLocalTime().getHour();
            startHoursList.add(hours);
            endHoursList.add(hours);
            businessStart = businessStart.plusHours(1);
        }

        //Populate Minutes
        for (int i = 0; i < 60; i += 5) {
            startMinutesList.add(i);
            endMinutesList.add(i);
        }

        startHoursCombo.setItems(startHoursList);
        startMinutesCombo.setItems(startMinutesList);
        endHoursCombo.setItems(endHoursList);
        endMinutesCombo.setItems(endMinutesList);
    }

    /**
     * Passes the user selected Appointment from the Appointments Information screen to this controller.
     *
     * <p> This method is used by the AppointmentsInfo controller to pass the user selected Appointment to this
     * controller. It fills in the fields in the form with the relevant information from the Appointment. It also
     * uses a boolean to alert this controller that the user is modifying an appointment instead of adding
     * one. </p>
     * @param updateAppointment The Appointment the user wants to modify
     */
    public void setUpdateAppointment(Appointment updateAppointment) {
        this.updateAppointment = updateAppointment;
        isUpdate = true;

        addUpdateLabel.setText("Update Appointment");
        addUpdateAppointment.setText("Update");

        appointmentIDLabel.setText(Integer.toString(updateAppointment.getAppointmentID()));
        titleTextfield.setText(updateAppointment.getTitle());
        descriptionTextfield.setText(updateAppointment.getDescription());
        locationTextfield.setText(updateAppointment.getLocation());
        typeTextfield.setText(updateAppointment.getType());
        customerTextfield.setText(Integer.toString(updateAppointment.getCustomerID()));
        userTextfield.setText(Integer.toString(updateAppointment.getUserID()));
        
        Contact updateContact = DAOHelper.getContactFromID(updateAppointment.getContactID());
        for (Contact contact : contactList) {
            if (contact.getContactID() == updateContact.getContactID()) {
                selectContactCombo.setValue(contact);
                break;
            }
        }

        // Database driver automatically converts from database time zone to local time zone
        LocalDateTime startDateTime = updateAppointment.getStartTime();
        LocalDateTime endDateTime = updateAppointment.getEndTime();
        startDatePicker.setValue(startDateTime.toLocalDate());
        endDatePicker.setValue(endDateTime.toLocalDate());

        startHoursCombo.setValue(startDateTime.getHour());
        startMinutesCombo.setValue(startDateTime.getMinute());
        endHoursCombo.setValue(endDateTime.getHour());
        endMinutesCombo.setValue(endDateTime.getMinute());
    }

    /**
     * Creates or modifies an Appointment and updates the database.
     *
     * <p> This method creates a new Appointment or modifies the selected Appointment using the information given by
     * the user. If the information doesn't pass the validation check, an alert is displayed with relevant information.
     * If the information is valid, the database is updated. </p>
     * @param actionEvent The event that called the method
     */
    public void onAddUpdateAppointment(ActionEvent actionEvent) {
        if (startHoursCombo.getSelectionModel().isEmpty() ||
            endHoursCombo.getSelectionModel().isEmpty()) {
            ErrorAlert.selectionError("a start and end time");
            return;
        }

        if (selectContactCombo.getSelectionModel().isEmpty()) {
            ErrorAlert.selectionError("a contact");
            return;
        }

        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            ErrorAlert.selectionError("a start and end date");
            return;
        }

        if (startMinutesCombo.getSelectionModel().isEmpty())
            startMinutesCombo.setValue(0);

        if (endMinutesCombo.getSelectionModel().isEmpty())
            endMinutesCombo.setValue(0);

        String title = titleTextfield.getText();
        String description = descriptionTextfield.getText();
        String location = locationTextfield.getText();
        String type = typeTextfield.getText();
        String customerID = customerTextfield.getText();
        String userID = userTextfield.getText();
        Contact contact = selectContactCombo.getValue();

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        LocalTime startTime = LocalTime.of(startHoursCombo.getValue(), startMinutesCombo.getValue());
        LocalTime endTime = LocalTime.of(endHoursCombo.getValue(), endMinutesCombo.getValue());
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        int appointmentID = -1;
        if (isUpdate)
            appointmentID = updateAppointment.getAppointmentID();

        //Validate form information
        String errorMessage = ErrorAlert.appointmentFormValidation(appointmentID, title, description, location, type, customerID,
                userID, start, end);
        if (!errorMessage.isEmpty()) {
            ErrorAlert.invalidForm(errorMessage);
            return;
        }

        Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end,
                Integer.parseInt(customerID), Integer.parseInt(userID), contact.getContactID());

        // Add or Update appointment based on isUpdate
        if (isUpdate) {
            DAOHelper.updateAppointment(appointment);
        }
        else {
            DAOHelper.addAppointment(appointment);
        }

        NavigationHelper.viewAppointmentsInfo(this, actionEvent);
    }

    /**
     * Returns the user to the appointment information page.
     *
     * <p> This method cancels the form without modifying the database and returns the user to view the appointments. </p>
     * @param actionEvent The event that called the method
     */
    public void onCancel(ActionEvent actionEvent) {
        NavigationHelper.viewAppointmentsInfo(this, actionEvent);
    }

    /**
     * Displays the selected time in the start time label.
     *
     * <p> This method displays the user selected time from the combo box in the start time label. </p>
     * @param actionEvent The event that called the method
     */
    public void onStartTimeSelect(ActionEvent actionEvent) {
        if (startHoursCombo.getSelectionModel().isEmpty())
            return;

        if (startMinutesCombo.getSelectionModel().isEmpty())
            startMinutesCombo.setValue(0);

        LocalTime selectedTime = LocalTime.of(startHoursCombo.getValue(), startMinutesCombo.getValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

        startTimeDisplay.setText("Time: " + formatter.format(selectedTime));
    }

    /**
     * Displays the selected time in the end time label.
     *
     * <p> This method displays the user selected time from the combo box in the end time label. </p>
     * @param actionEvent The event that called the method
     */
    public void onEndTimeSelect(ActionEvent actionEvent) {
        if (endHoursCombo.getSelectionModel().isEmpty())
            return;

        if (endMinutesCombo.getSelectionModel().isEmpty())
            endMinutesCombo.setValue(0);

        LocalTime selectedTime = LocalTime.of(endHoursCombo.getValue(), endMinutesCombo.getValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

        endTimeDisplay.setText("Time: " + formatter.format(selectedTime));
    }
}
