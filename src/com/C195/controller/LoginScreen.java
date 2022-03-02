package com.C195.controller;

import com.C195.helper.DAOHelper;
import com.C195.helper.ErrorAlert;
import com.C195.helper.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the log in screen.
 *
 * <p> This class is the controller for the GUI screen that lets a user log in. It checks the user ID and password
 * against the database. It logs login attempts in a file. It uses a ResourceBundle to automatically translate the
 * screen between English and French. </p>
 */
public class LoginScreen implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Label locationLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordTextfield;
    @FXML
    private Label userIDLabel;
    @FXML
    private TextField userIDTextfield;
    @FXML
    private Label zoneDisplay;

    ResourceBundle rbLanguage;

    /**
     * The initializer for the controller.
     *
     * <p> This method initializes the controller class. It sets up the ResourceBundle used for translation. </p>
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneDisplay.setText(ZoneId.systemDefault().toString());

        Locale locale = Locale.getDefault();

        // Set locale to English if a translation property file doesn't exist.
        if (!locale.getLanguage().equals("fr")) {
            locale = Locale.ENGLISH;
        }

        rbLanguage = ResourceBundle.getBundle("com/C195/resources/Login", locale);

        loginLabel.setText(rbLanguage.getString("Log In"));
        userIDLabel.setText(rbLanguage.getString("User ID"));
        passwordLabel.setText(rbLanguage.getString("Password"));
        loginButton.setText(rbLanguage.getString("Log In"));
        locationLabel.setText(rbLanguage.getString("Location"));
        exitButton.setText(rbLanguage.getString("Exit"));
    }

    /**
     * Checks the user ID and password against the database.
     *
     * <p> This method checks the entered user ID and password against the database to make sure they are valid. It
     * records login attempts in a file. If the log in is successful, it shows an alert informing the user showing
     * whether there is an appointment in the next 15 minutes. Then it loads the AppointmentsInfo controller, showing
     * the appointments from the database. </p>
     * @param actionEvent The event that called the method
     */
    public void onLogIn(ActionEvent actionEvent) {
        String userName = userIDTextfield.getText();
        String password = passwordTextfield.getText();
        LocalDateTime dateTime = LocalDateTime.now();
        Path loginLog = Paths.get("login_activity.txt");

        // Create an entry in the login logs
        try (BufferedWriter bw = Files.newBufferedWriter(loginLog, StandardCharsets.UTF_8, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND, StandardOpenOption.WRITE);
             PrintWriter writer = new PrintWriter(bw)) {

           writer.println("Log-in attempt on : " + dateTime);
           writer.println("User ID: " + userName);

            boolean isValid = DAOHelper.validateUserName(userName);
            if (!isValid) {
                writer.println("Log-in attempt unsuccessful: invalid user ID.\n");
                writer.flush();
                ErrorAlert.incorrectUserID(rbLanguage);
                return;
            }
            isValid = DAOHelper.validateLogin(userName, password);
            if (!isValid) {
                writer.println("Log-in attempt unsuccessful: invalid password.\n");
                writer.flush();
                ErrorAlert.incorrectPassword(rbLanguage);
                return;
            }

            writer.println("Log-in attempt successful.\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ErrorAlert.appointmentSoon();

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

}
