

package com.example.main;

import java.io.IOException;

import com.example.dao.UtilisateurDAO;
import com.example.model.Utilisateur;

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

public class UserController {

    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, Integer> colId;
    @FXML
    private TableColumn<Utilisateur, String> colFirstName;
    @FXML
    private TableColumn<Utilisateur, String> colLastName;
    @FXML
    private TableColumn<Utilisateur, String> colEmail;
    @FXML
    private TableColumn<Utilisateur, String> colType;

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtType;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
    private Utilisateur selectedUser = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        loadUserData();
    }

    private void loadUserData() {
        userList.clear();
        userList.addAll(utilisateurDAO.getAll());
        userTable.setItems(userList);
    }

  

    @FXML
    private void handleUpdateUser() {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            txtFirstName.setText(selectedUser.getPrenom());
            txtLastName.setText(selectedUser.getNom());
            txtEmail.setText(selectedUser.getEmail());
            txtType.setText(selectedUser.getType());
        } else {
            showAlert("No Selection", "Please select a user to update.");
        }
    }

    @FXML
    private void handleDeleteUser() {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            utilisateurDAO.delete(selectedUser.getId());
            loadUserData();
        } else {
            showAlert("No Selection", "Please select a user to delete.");
        }
    }

    @FXML
    private void handleSaveUser() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String type = txtType.getText();
      
        if (validateInput(firstName, lastName, email, type)) {
            if (selectedUser != null) {
                selectedUser.setPrenom(firstName);
                selectedUser.setNom(lastName);
                selectedUser.setEmail(email);
                selectedUser.setType(type);
                utilisateurDAO.update(selectedUser);
              
            } 
            loadUserData();
            clearForm();
        }
    }

    @FXML
    private void handleRefresh() {
        loadUserData();
    }

    private void clearForm() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtType.clear();
    }

    private boolean validateInput(String firstName, String lastName, String email, String type) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || type.isEmpty()) {
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

  
    
}

