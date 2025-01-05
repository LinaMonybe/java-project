package com.example.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

    private static Connection connection;

    // Establish a connection to the database
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load PostgreSQL driver
                Class.forName("org.postgresql.Driver");
                
                // Establish connection
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
                connection.setAutoCommit(false); // Disable auto-commit for transaction management
            } catch (SQLException | ClassNotFoundException e) {
                // Basic error handling, print the exception if occurs
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Close the database connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close(); // Close the connection
            } catch (SQLException e) {
                // Print any exception that occurs while closing the connection
                e.printStackTrace();
            }
        }
    }
}
