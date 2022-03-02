package com.C195.main;

import com.C195.helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for a scheduling management system.
 *
 * <p> This is the main class for a scheduling system for managing Customers, Appointments, and Contacts. The system
 * allows the user to log in and connect to a database where they can add, update, and delete customers and appointments.
 * It also allows the user to view various information about customers, appointments, and contacts. </p>
 * <p> JavaDoc comments found in Scheduler_App/JavaDoc/index.html </p>
 */
public class Main extends Application {


    /**
     * Main method for the Main class.
     *
     * <p> This is the main method for the Main class and lauches the program. </p>
     * @param args Arguments for launching the program
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }

    /**
     * Launches the GUI.
     *
     * <p> This method starts the program and launches the GUI. </p>
     * @param stage The primary stage for the GUI
     * @throws Exception Exceptions that may occur in the program
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/C195/view/LoginScreen.fxml"));
        stage.setTitle("Scheduler");
        stage.setScene(new Scene(root, 1200, 700));
        stage.show();
    }

//    // Used to test the program
//    private static void test() {
//        TimeZone.setDefault(TimeZone.getTimeZone("PST"));
//
//        Locale.setDefault(Locale.FRANCE);
//    }
}
