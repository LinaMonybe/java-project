package com.example.main;

import java.io.IOException;

import com.example.dao.EvenementDAO;
import com.example.model.Evenement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EventUserController {

    @FXML
    private TableView<Evenement> evenementTable;

    @FXML
    private TableColumn<Evenement, String> colEventName;

    @FXML
    private TableColumn<Evenement, String> colEventDate;

    @FXML
    private TableColumn<Evenement, String> colEventDescription;

    private EvenementDAO evenementDAO = new EvenementDAO();

    @FXML
    public void initialize() {
        // Set up table columns
        colEventName.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        colEventDate.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        colEventDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load data into the table
        loadEvenementData();
    }

    private void loadEvenementData() {
        ObservableList<Evenement> evenementList = FXCollections.observableArrayList(evenementDAO.getAll());
        evenementTable.setItems(evenementList);
    }

     @FXML
    public void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/MainUser.fxml")); // Ensure path is correct
            Parent root = loader.load();
    
            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            // Set the new scene without closing the stage
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}