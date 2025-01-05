

package com.example.main;

import java.io.IOException;

import com.example.dao.SalleDAO;
import com.example.model.Salle;

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

public class SalleController {

    @FXML
    private TableView<Salle> salleTable;
    @FXML
    private TableColumn<Salle, Integer> colId;
    @FXML
    private TableColumn<Salle, String> colName;
    @FXML
    private TableColumn<Salle, Integer> colCap;
   
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCapacity;
   
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private SalleDAO salleDAO = new SalleDAO();
    private ObservableList<Salle> salleList = FXCollections.observableArrayList();
    private Salle selectedSalle = null;

    @FXML
    public void initialize() {
        // Set up table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("idSalle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nomSalle"));
        colCap.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        // Load data into the table
        loadSalleData();
    }

    private void loadSalleData() {
        salleList.clear();
        salleList.addAll(salleDAO.getAll());
        salleTable.setItems(salleList);
    }

     @FXML
    private void handleAddSalle() {
        String nomSalle = txtName.getText();
        String capaciteStr = txtCapacity.getText();
        
        if (nomSalle.isEmpty() || capaciteStr.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            int capacite = Integer.parseInt(capaciteStr);
            
            // Create a Salle object
            Salle newSalle = new Salle(nomSalle, capacite);
            
            // Add the new salle to the database
            salleDAO.add(newSalle);
            
            showAlert("Success", "Room added successfully!");
            
            // Optionally, clear fields after submission
            txtName.clear();
            txtCapacity.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Capacity must be a number.");
        }
    }


    
    // @FXML
    // private void handleAddSalle() {
    //     clearForm();
    //     selectedSalle = null; // Adding a new user
    // }

    @FXML
    private void handleUpdateSalle() {
        selectedSalle = salleTable.getSelectionModel().getSelectedItem();
        if (selectedSalle != null) {
            txtName.setText(selectedSalle.getNomSalle());
            txtCapacity.setText(String.valueOf(selectedSalle.getCapacite()));
        } else {
            showAlert("No Selection", "Please select a user to update.");
        }
    }

    @FXML
    private void handleDeleteSalle() {
        selectedSalle = salleTable.getSelectionModel().getSelectedItem();
        if (selectedSalle != null) {
            salleDAO.delete(selectedSalle.getIdSalle()); // Make sure delete uses the idSalle
            loadSalleData(); // Reload the data to reflect the changes
        } else {
            showAlert("No Selection", "Please select a salle to delete.");
        }
    }
    
@FXML
private void handleSaveSalle() {
    String nomSalle = txtName.getText();
    String capacite = txtCapacity.getText();

    // Validate input
    if (validateInput(nomSalle, capacite)) {
        if (selectedSalle == null) {
            // Add new salle
            Salle newSalle = new Salle(nomSalle, Integer.parseInt(capacite)); // Assuming capacite is an Integer
            salleDAO.add(newSalle); // Make sure the DAO add method handles saving
        } else {
            // Update existing salle
            selectedSalle.setNomSalle(nomSalle);
            selectedSalle.setCapacite(Integer.parseInt(capacite)); // Convert capacite to Integer
            salleDAO.update(selectedSalle); // Make sure the DAO update method handles updating
        }
        loadSalleData(); // Reload data
        clearForm(); // Clear input fields
    }
}


    @FXML
    private void handleRefresh() {
        loadSalleData();
    }

    private void clearForm() {
        txtName.clear();
        txtCapacity.clear();
    }

    private boolean validateInput(String name, String capacity) {
        if (name.isEmpty() || capacity.isEmpty()) {
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

