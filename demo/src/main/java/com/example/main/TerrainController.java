package com.example.main;

import java.io.IOException;

import com.example.dao.TerrainDAO;
import com.example.model.Terrain;

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

public class TerrainController {

    @FXML
    private TableView<Terrain> terrainTable;
    @FXML
    private TableColumn<Terrain, Integer> colId;
    @FXML
    private TableColumn<Terrain, String> colName;
    @FXML
    private TableColumn<Terrain, String> colType;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtType;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private TerrainDAO terrainDAO = new TerrainDAO();
    private ObservableList<Terrain> terrainList = FXCollections.observableArrayList();
    private Terrain selectedTerrain = null;

    @FXML
    public void initialize() {
        // Set up table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("idTerrain"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nomTerrain"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Load data into the table
        loadTerrainData();
    }

    private void loadTerrainData() {
        terrainList.clear();
        terrainList.addAll(terrainDAO.getAll());
        terrainTable.setItems(terrainList);
    }

    @FXML
    private void handleAddTerrain() {
        String nomTerrain = txtName.getText();
        String typeTerrain = txtType.getText();

        if (nomTerrain.isEmpty() || typeTerrain.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Create a Terrain object
        Terrain newTerrain = new Terrain(0, nomTerrain, typeTerrain);

        // Add the new terrain to the database
        terrainDAO.add(newTerrain);

        showAlert("Success", "Terrain added successfully!");

        // Optionally, clear fields after submission
        txtName.clear();
        txtType.clear();
    }

    @FXML
    private void handleUpdateTerrain() {
        selectedTerrain = terrainTable.getSelectionModel().getSelectedItem();
        if (selectedTerrain != null) {
            txtName.setText(selectedTerrain.getNomTerrain());
            txtType.setText(selectedTerrain.getType());
        } else {
            showAlert("No Selection", "Please select a terrain to update.");
        }
    }

    @FXML
    private void handleDeleteTerrain() {
        selectedTerrain = terrainTable.getSelectionModel().getSelectedItem();
        if (selectedTerrain != null) {
            terrainDAO.delete(selectedTerrain.getIdTerrain()); // Make sure delete uses the idTerrain
            loadTerrainData(); // Reload the data to reflect the changes
        } else {
            showAlert("No Selection", "Please select a terrain to delete.");
        }
    }

    @FXML
    private void handleSaveTerrain() {
        String nomTerrain = txtName.getText();
        String typeTerrain = txtType.getText();
    
        // Validate input
        if (validateInput(nomTerrain, typeTerrain)) {
            if (selectedTerrain == null) {
                // Add new terrain
                Terrain newTerrain = new Terrain(0, nomTerrain, typeTerrain); // Assuming id is auto-generated
                terrainDAO.add(newTerrain); // Make sure the DAO add method handles saving
            } else {
                // Update existing terrain
                selectedTerrain.setNomTerrain(nomTerrain);
                selectedTerrain.setType(typeTerrain);
                terrainDAO.update(selectedTerrain); // Make sure the DAO update method handles updating
            }
            loadTerrainData(); // Reload data
            clearForm(); // Clear input fields
        }
    }
    
    @FXML
    private void handleRefresh() {
        loadTerrainData();
    }
    
    private void clearForm() {
        txtName.clear();
        txtType.clear();
    }
    
    private boolean validateInput(String name, String type) {
        if (name.isEmpty() || type.isEmpty()) {
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
