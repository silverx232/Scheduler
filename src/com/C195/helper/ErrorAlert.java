package com.C195.helper;

import com.C195.model.Appointment;
import com.C195.model.Division;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class that contains methods to display Alert windows.
 *
 * <p> This class is used by the program controllers to display Alert windows and perform validation checking for the forms.</p>
 */
public abstract class ErrorAlert {

    /**
     * Informs the user of a selection error.
     *
     * <p> This method displays a window informing the user that they need to select an object before they can
     * modify or delete it. </p>
     * @param objectType Contains the string defining what type of object it is, depending on which controller is calling it
     */
    public static void selectionError(String objectType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setHeaderText("Selection Error");
        alert.setContentText("Please select " + objectType + ".");
        alert.showAndWait();
    }

    /**
     * Informs that user that the searched for item does not exist.
     *
     * <p> This method displays a window informing the user that the object they searched for does not exist. </p>
     * @param objectType Contains the string for the type of object, depending on which controller is calling it
     */
    public static void searchNotFoundError(String objectType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Search Not Found");
        alert.setContentText("No such " + objectType + " exists.");
        alert.showAndWait();
    }

    /**
     * Asks for confirmation from the user before deleting an object.
     *
     * <p> This method asks for confirmation from the user before deleting an object. </p>
     * @param objectType Contains the string for the type of object, depending on which controller is calling it
     * @return Returns true if the user selects OK
     */
    public static boolean deleteConfirmation(String objectType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this " + objectType + "?");
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && (result.get() == ButtonType.OK);
    }

    /**
     * Asks for confirmation from the user before deleting a customer.
     *
     * <p> This method asks for confirmation from the user before deleting a customer. It also informs the user that
     * all of a customer's appointments will be deleted along with the customer. </p>
     * @return Returns true if the user selects OK
     */
    public static boolean deleteCustomerConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("Deleting a customer will delete all of the customer's appointments as well. " +
                "Are you sure you want to delete this customer?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && (result.get() == ButtonType.OK);
    }

    /**
     * Alerts the user that the given user ID doesn't exist.
     *
     * <p> This method pops up an alert informing the user that the user ID used for log in doesn't exist in the
     * databsase. </p>
     * @param rbLanguage Contains the ResourceBundle used for automatic translation
     */
    public static void incorrectUserID(ResourceBundle rbLanguage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(rbLanguage.getString("Login Error"));
        alert.setContentText(rbLanguage.getString("The given User ID does not exist."));
        alert.showAndWait();
    }

    /**
     * Alerts the user that the given password is incorrect.
     *
     * <p> This method pops up an alert informing the user that the password used for log in is incorrect for the
     * given user ID. </p>
     * @param rbLanguage Contains the ResourceBundle used for automatic translation
     */
    public static void incorrectPassword(ResourceBundle rbLanguage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(rbLanguage.getString("Login Error"));
        alert.setContentText(rbLanguage.getString("The given password is incorrect."));
        alert.showAndWait();
    }

    /**
     * Alerts the user that the information given is invalid.
     *
     * <p> This method pops up an alert informing the user that the information given in the form is invalid. It
     * displays an error message informing the user of what information needs to be changed to be valid. </p>
     * @param errorMessage The error message informing the user of what parts of the form need to be changed
     */
    public static void invalidForm(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid Information");
        alert.setContentText(errorMessage);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     *Validates the information entered by the user.
     *
     * <p> This method validates the information entered by the user when adding or modifying a Customer.
     * A name, phone, address, and postal code must contain data. A Division must be selected from the dropdown menu. </p>
     * @param name Name of the Customer
     * @param phone Telephone number of the Customer
     * @param address Address of the Customer
     * @param postalCode Postal code for the address
     * @param division The division, state, or province for the address
     * @return Returns a String containing messages for any failed checks
     */
    public static String customerFormValidation(String name, String phone, String address, String postalCode,
                                              Division division) {
        StringBuilder errorMessage = new StringBuilder();

        if (name.isBlank())
            errorMessage.append("Customer must have a name. ");

        if (phone.isBlank())
            errorMessage.append("Customer must have a phone number. ");

        if (address.isBlank())
            errorMessage.append("Customer must have an address. ");

        if (postalCode.isBlank())
            errorMessage.append("Customer must have a postal code. ");

        if (division == null)
            errorMessage.append("Please select a country and state or province. ");

        return errorMessage.toString();
    }

    /**
     * Validates the information entered by the user.
     *
     * <p> This method validates the information entered by the user when adding or modifying an Appointment.
     * The title, description, location, and type must contain data. Customer and User IDs must contain integers.
     * Customer and User IDs must be valid IDs in the database. An appointment cannot overlap with another appointment
     * for the same customer. The end date and time must be after the start date and time.
     * @param updateAppointmentID The appointment's ID. This information is not given by the user but is needed for validation. It will be -1 if the appointment is being added, not updated.
     * @param title Title of the appointment
     * @param description Description of the appointment
     * @param location Location of the appointment
     * @param type The type of appointment
     * @param customerID The appointment's customer's ID
     * @param userID The appointment's user's ID
     * @param start LocalDateTime for the start of the appointment
     * @param end LocalDateTime for the end of the appointment
     * @return Returns a String containing messages from any failed checks
     */
    public static String appointmentFormValidation(int updateAppointmentID, String title, String description, String location, String type,
                                                 String customerID, String userID, LocalDateTime start,
                                                 LocalDateTime end) {

        StringBuilder errorMessage = new StringBuilder();
        int customerIdInteger = -1;
        int userIdInteger = -1;
        boolean isValidCustomerID = false;
        boolean isValidUserID = false;

        if (title.isBlank())
            errorMessage.append("Appointment must have a title. ");

        if (description.isBlank())
            errorMessage.append("Appointment must have a description. ");

        if (location.isBlank())
            errorMessage.append("Appointment must have a location. ");

        if (type.isBlank())
            errorMessage.append("Appointment must have a type. ");

        try {
            customerIdInteger = Integer.parseInt(customerID);
            isValidCustomerID = true;
        } catch (NumberFormatException e) {
            errorMessage.append("Customer ID must be a valid integer. ");
        }

        try {
            userIdInteger = Integer.parseInt(userID);
            isValidUserID = true;
        } catch (NumberFormatException e) {
            errorMessage.append("User ID must be a valid integer. ");
        }

        // Must be a valid customer ID
        if (isValidCustomerID)
            isValidCustomerID = DAOHelper.validateCustomer(customerIdInteger);

        // Appointment start must be before appointment end
        if (end.isBefore(start))
            errorMessage.append("Appointment start must be before appointment end. ");

        // Appointments for a customer cannot overlap
        // If appointment is being updated, exclude the appointment currently being updated
        if (isValidCustomerID) {
            ObservableList<Appointment> appointmentList = DAOHelper.getAppointmentsFromCustomer(customerIdInteger);

            for (Appointment current : appointmentList) {
                // If appointment is the one being updated, skip
                if (current.getAppointmentID() == updateAppointmentID)
                    continue;

                LocalDateTime currentStart = current.getStartTime();
                LocalDateTime currentEnd = current.getEndTime();

                if ( ((start.isAfter(currentStart) || start.isEqual(currentStart)) && start.isBefore(currentEnd)) ||
                        (end.isAfter(currentStart) && (end.isBefore(currentEnd) || end.isEqual(currentEnd))) ||
                        (start.isBefore(currentStart) && end.isAfter(currentEnd)) ) {
                    errorMessage.append("Appointments for a customer cannot overlap. Overlapping appointment ID: " +
                            current.getAppointmentID() + ". ");
                    break;
                }
            }
        }
        else
            errorMessage.append("Customer ID is not a valid customer.");

        // Must be a valid user ID
        if (isValidUserID)
            isValidUserID = DAOHelper.validateUser(userIdInteger);

        if (!isValidUserID)
            errorMessage.append("User ID is not a valid user.");

        return errorMessage.toString();
    }


    /**
     * Alerts the user whether an appointment is happening soon.
     *
     * <p> This method pops up an alert after log in informing the user that an appointment is currently happening or
     * will start in the next 15 minutes. Or it informs the user there is no appointment happening soon. </p>
     */
    public static void appointmentSoon() {
        ObservableList<Appointment> appointmentList = DAOHelper.getAllAppointments();
        LocalDateTime now = LocalDateTime.now();
        String message = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

        // Check if appointment is currently happening or if an appointment starts within 15 minutes
        for (Appointment current : appointmentList) {
            LocalDateTime start = current.getStartTime();
            LocalDateTime end = current.getEndTime();

            if ( ((start.isEqual(now) || start.isBefore(now)) && end.isAfter(now)) ||
                    ((start.isEqual(now) || start.isAfter(now)) && start.isBefore(now.plusMinutes(15).plusSeconds(1))) ) {
                message += "Appointment soon.\nAppointment ID: " + current.getAppointmentID() + ", Start: " +
                        current.getStartTime().format(formatter) + ", End: " + current.getEndTime().format(formatter) + "\n";
            }
        }

        if (message.isBlank())
            message += "There are no appointments happening in the next 15 minutes.";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Upcoming Appointments");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Alerts the user when an appointment has been cancelled.
     *
     * <p> This method informs the user that the appointment they deleted has been successfully cancelled and deleted
     * from the database. </p>
     * @param appointment The Appointment that has been deleted from the database
     */
    public static void appointmentCancelled(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Appointment Cancelled");
        alert.setContentText("Appointment with ID: " + appointment.getAppointmentID() + " and Type: " +
                appointment.getType() + " was cancelled.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
