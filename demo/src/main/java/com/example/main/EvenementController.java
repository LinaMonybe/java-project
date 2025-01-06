package com.example.main;

import java.io.IOException;
import java.util.Date;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EvenementController {

    @FXML
    private TableView<Evenement> evenementTable;
    @FXML
    private TableColumn<Evenement, Integer> colEventId;
    @FXML
    private TableColumn<Evenement, String> colEventName;
    @FXML
    private TableColumn<Evenement, Date> colEventDate;
    @FXML
    private TableColumn<Evenement, String> colEventDescription;
    @FXML
    private TableColumn<Evenement, Integer> colEventUserId;

    @FXML
    private TextField txtEventName;
    @FXML
    private TextField txtEventDate;
    @FXML
    private TextField txtEventDescription;
    @FXML
    private TextField txtUserId;

    @FXML
    private Button btnSave;
   
    private EvenementDAO evenementDAO = new EvenementDAO();
    private ObservableList<Evenement> evenementList = FXCollections.observableArrayList();
    private Evenement selectedEvenement = null;

    @FXML
    public void initialize() {
        // Set up table columns
        colEventId.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        colEventName.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        colEventDate.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        colEventDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colEventUserId.setCellValueFactory(new PropertyValueFactory<>("idUser"));

        // Load data into the table
        loadEvenementData();
    }

    private void loadEvenementData() {
        evenementList.clear();
        evenementList.addAll(evenementDAO.getAll());
        evenementTable.setItems(evenementList);
    }

    @FXML
    private void handleAddEvenement() {
        String nomEvent = txtEventName.getText();
        String eventDateStr = txtEventDate.getText();
        String description = txtEventDescription.getText();
        String userIdStr = txtUserId.getText();

        if (nomEvent.isEmpty() || eventDateStr.isEmpty() || description.isEmpty() || userIdStr.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            int idUser = Integer.parseInt(userIdStr);
            Date dateEvent = new Date(eventDateStr);  // Parse date correctly
            
            // Create a new Evenement object
            Evenement newEvenement = new Evenement(0, nomEvent, dateEvent, description, idUser);

            // Add the new event to the database
            evenementDAO.add(newEvenement);

            showAlert("Success", "Event added successfully!");
            
            // Optionally, clear fields after submission
            clearForm();
        } catch (Exception e) {
            showAlert("Error", "Invalid input data.");
        }
    }

    @FXML
    private void handleUpdateEvenement() {
        selectedEvenement = evenementTable.getSelectionModel().getSelectedItem();
        if (selectedEvenement != null) {
            txtEventName.setText(selectedEvenement.getNomEvent());
            txtEventDate.setText(selectedEvenement.getDateEvent().toString());
            txtEventDescription.setText(selectedEvenement.getDescription());
            txtUserId.setText(String.valueOf(selectedEvenement.getIdUser()));
        } else {
            showAlert("No Selection", "Please select an event to update.");
        }
    }

    @FXML
    private void handleDeleteEvenement() {
        selectedEvenement = evenementTable.getSelectionModel().getSelectedItem();
        if (selectedEvenement != null) {
            evenementDAO.delete(selectedEvenement.getIdEvent()); // Make sure delete uses the idEvent
            loadEvenementData(); // Reload the data to reflect the changes
        } else {
            showAlert("No Selection", "Please select an event to delete.");
        }
    }

    @FXML
    private void handleSaveEvenement() {
        String nomEvent = txtEventName.getText();
        String eventDate = txtEventDate.getText();
        String description = txtEventDescription.getText();
        String userId = txtUserId.getText();

        if (validateInput(nomEvent, eventDate, description, userId)) {
            if (selectedEvenement == null) {
                // Add new event
                Evenement newEvenement = new Evenement(0, nomEvent, new Date(eventDate), description, Integer.parseInt(userId));
                evenementDAO.add(newEvenement);
            } else {
                // Update existing event
                selectedEvenement.setNomEvent(nomEvent);
                selectedEvenement.setDateEvent(new Date(eventDate));
                selectedEvenement.setDescription(description);
                selectedEvenement.setIdUser(Integer.parseInt(userId));
                evenementDAO.update(selectedEvenement);
            }
            loadEvenementData();
            clearForm();
        }
    }

    @FXML
    private void handleRefresh() {
        loadEvenementData();
    }

    private void clearForm() {
        txtEventName.clear();
        txtEventDate.clear();
        txtEventDescription.clear();
        txtUserId.clear();
    }

    private boolean validateInput(String name, String date, String description, String userId) {
        if (name.isEmpty() || date.isEmpty() || description.isEmpty() || userId.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml")); // Ensure path is correct
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
