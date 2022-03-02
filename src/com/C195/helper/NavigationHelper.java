package com.C195.helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that contains methods to navigate the app.
 *
 * <p> This class is used by the fxml controllers to help navigate through the different screens.</p>
 */
public abstract class NavigationHelper {

    /**
     * Exits the program.
     *
     * <p> This method exits the program. </p>
     * @param actionEvent The event from the controller that called the method
     */
    public static void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Loads the Customer Information screen.
     *
     * <p> This method loads the customer information screen. </p>
     * @param controller The controller that called the method
     * @param actionEvent The event from the controller that called the method
     */
    public static void viewCustomerInfo(Object controller, ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(controller.getClass().getResource("/com/C195/view/CustomerInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Customers");
        stage.show();
    }

    /**
     * Loads the Appointment Information screen.
     *
     * <p> This method loads the appointment information screen. </p>
     * @param controller The controller that called the method
     * @param actionEvent The event from the controller that called the method
     */
    public static void viewAppointmentsInfo(Object controller, ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(controller.getClass().getResource("/com/C195/view/AppointmentsInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Appointments");
        stage.show();
    }

    /**
     * Loads the Contacts Information screen.
     *
     * <p> This method loads the contacts information screen. </p>
     * @param controller The controller that called the method
     * @param actionEvent The event from the controller that called the method
     */
    public static void viewContactsInfo(Object controller, ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(controller.getClass().getResource("/com/C195/view/ContactsInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Contacts");
        stage.show();
    }

    /**
     * Loads the reports screen.
     *
     * <p> This method loads the reports screen. </p>
     * @param controller The controller that called the method
     * @param actionEvent The event from the controller that called the method
     */
    public static void viewReportsScreen(Object controller, ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(controller.getClass().getResource("/com/C195/view/ReportsScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Reports");
        stage.show();
    }
}
