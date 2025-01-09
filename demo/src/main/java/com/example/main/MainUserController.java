package com.example.main;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainUserController {

    @FXML
    public void checkEvents(ActionEvent event) {
        loadNewStage("EventUser.fxml", event);
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