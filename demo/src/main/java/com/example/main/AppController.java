package com.example.main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {

    @FXML
    public void manageUsers(ActionEvent event) {
        loadNewStage("user.fxml", event);
    }

    @FXML
    public void manageRooms(ActionEvent event) {
        loadNewStage("rooms.fxml", event);
    }

    @FXML
    public void manageFields(ActionEvent event) {
        loadNewStage("fields.fxml", event);
    }

    @FXML
    public void manageReservations(ActionEvent event) {
        loadNewStage("reservations.fxml", event);
    }

    @FXML
    public void exitApp(ActionEvent event) {
        System.exit(0);
    }

    private void loadNewStage(String fxmlFile, ActionEvent event) {
        try {
            // Load the new FXML file (e.g., user.fxml, rooms.fxml, etc.)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/" + fxmlFile));
            Parent root = loader.load();
            
            // Create a new stage for the new FXML file
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Close the current stage (window) and open the new one
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            stage.show();  // Show the new stage
        } catch (IOException e) {
            e.printStackTrace();  // Handle potential exceptions
        }
    }
}
