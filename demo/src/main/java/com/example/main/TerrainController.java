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

    @FXML
    private Button btnReserve;

    private TerrainDAO terrainDAO = new TerrainDAO();
    private ObservableList<Terrain> terrainList = FXCollections.observableArrayList();
    private Terrain selectedTerrain = null;

    @FXML
    public void initialize() {
        // Set up table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("idTerrain"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nomTerrain"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
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
        Terrain newTerrain = new Terrain(0, nomTerrain, typeTerrain);
        terrainDAO.add(newTerrain);
        showAlert("Success", "Terrain added successfully!");
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
            terrainDAO.delete(selectedTerrain.getIdTerrain()); 
            loadTerrainData(); 
        } else {
            showAlert("No Selection", "Please select a terrain to delete.");
        }
    }

    @FXML
    private void handleSaveTerrain() {
        String nomTerrain = txtName.getText();
        String typeTerrain = txtType.getText();
    
        if (validateInput(nomTerrain, typeTerrain)) {
            if (selectedTerrain == null) {
                Terrain newTerrain = new Terrain(0, nomTerrain, typeTerrain); 
                terrainDAO.add(newTerrain); 
            } else {
                selectedTerrain.setNomTerrain(nomTerrain);
                selectedTerrain.setType(typeTerrain);
                terrainDAO.update(selectedTerrain);
            }
            loadTerrainData(); 
            clearForm(); 
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml")); 
            Parent root = loader.load();
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ReserveFeild(ActionEvent event){
        Terrain selectedTerrain = terrainTable.getSelectionModel().getSelectedItem();
        if (selectedTerrain == null) {
            showAlert("No Selection", "Please select a room to reserve.");
            return;
        }
     
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/AddRerservFeild.fxml"));
            Parent root = loader.load();
            AddRerservFeildControl addReservController = loader.getController();
            addReservController.setfeild(selectedTerrain);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
