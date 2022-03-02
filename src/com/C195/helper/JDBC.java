package com.C195.helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Handles the Connection to the database
 *
 * <p> This class handles the Connection to the database. The following code was given by C195 instructors at WGU. </p>
 */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * Opens the connection.
     *
     * <p> This method opens the Connection to the database. </p>
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
//            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Closes the connection.
     *
     * <p> This method closes the Connection to the database. </p>
     */
    public static void closeConnection() {
        try {
            connection.close();
//            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
