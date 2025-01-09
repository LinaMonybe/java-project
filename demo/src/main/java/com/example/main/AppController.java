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
        loadNewStage("salle.fxml", event);
    }

    @FXML
    public void manageFields(ActionEvent event) {
        loadNewStage("terrain.fxml", event);
    }

    @FXML
    public void manageReservations(ActionEvent event) {
        loadNewStage("reservation.fxml", event);
    }

    @FXML
    public void exitApp(ActionEvent event) {
        System.exit(0);
    }

    private void loadNewStage(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/" + fxmlFile));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            stage.show(); 
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
