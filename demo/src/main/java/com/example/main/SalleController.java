

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

    @FXML
    private Button btnReserve;

    private SalleDAO salleDAO = new SalleDAO();
    private ObservableList<Salle> salleList = FXCollections.observableArrayList();
    private Salle selectedSalle = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idSalle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nomSalle"));
        colCap.setCellValueFactory(new PropertyValueFactory<>("capacite"));
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
            Salle newSalle = new Salle(0,nomSalle, capacite);
    
            salleDAO.add(newSalle);
            
            showAlert("Success", "Room added successfully!");
            
            txtName.clear();
            txtCapacity.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Capacity must be a number.");
        }
    }


   
 
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
            salleDAO.delete(selectedSalle.getIdSalle());
            loadSalleData(); 
        } else {
            showAlert("No Selection", "Please select a salle to delete.");
        }
    }
    
@FXML
private void handleSaveSalle() {
    String nomSalle = txtName.getText();
    String capacite = txtCapacity.getText();

    if (validateInput(nomSalle, capacite)) {
        if (selectedSalle == null) {
            Salle newSalle = new Salle(0,nomSalle, Integer.parseInt(capacite)); 
            salleDAO.add(newSalle); 
        } else {
            selectedSalle.setNomSalle(nomSalle);
            selectedSalle.setCapacite(Integer.parseInt(capacite)); 
            salleDAO.update(selectedSalle);
        }
        loadSalleData(); 
        clearForm();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml")); 
            Parent root = loader.load();
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ReserveRoom(ActionEvent event) {
        Salle selectedSalle = salleTable.getSelectionModel().getSelectedItem();
        if (selectedSalle == null) {
            showAlert("No Selection", "Please select a room to reserve.");
            return;
        }
     
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/AddRerserv.fxml"));
            Parent root = loader.load();
            AddRsereveController addReservController = loader.getController();
            addReservController.setSalle(selectedSalle);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    
    
}

