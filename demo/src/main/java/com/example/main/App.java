package com.example.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml"));
            Parent root = loader.load();

            // Set the scene with the loaded FXML
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Set the title of the application window
            primaryStage.setTitle("Main Menu");

            // Show the stage (the application window)
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}


