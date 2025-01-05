

package com.example.main;

import com.example.dao.UtilisateurDAO;
import com.example.model.Utilisateur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
        // Set up table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Load data into the table
        loadUserData();
    }

    private void loadUserData() {
        userList.clear();
        userList.addAll(utilisateurDAO.getAll());
        userTable.setItems(userList);
    }

    @FXML
    private void handleAddUser() {
        clearForm();
        selectedUser = null; // Adding a new user
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
            if (selectedUser == null) {
                // Add new user
                Utilisateur newUser = new Utilisateur(0, firstName, lastName, email, type);
                utilisateurDAO.add(newUser);
            } else {
                // Update existing user
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
    private void handleCancel() {
        clearForm();
        selectedUser = null;
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
}


// package com.example.main;

// import java.io.IOException;

// import com.example.dao.UtilisateurDAO;
// import com.example.model.Utilisateur;

// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.ListView;
// import javafx.scene.control.TextField;
// import javafx.stage.Stage;

// public class UserController {

//     // Declare the FXML components
//     @FXML private TextField firstNameField;
//     @FXML private TextField lastNameField;
//     @FXML private TextField emailField;
//     @FXML private TextField typeField;
//     @FXML private ListView<Utilisateur> userListView;
    
//     private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

//     // Add user action
//     @FXML
//     public void handleAddUser(ActionEvent event) {
//         String firstName = firstNameField.getText();
//         String lastName = lastNameField.getText();
//         String email = emailField.getText();
//         String type = typeField.getText();
        
//         // Create new Utilisateur and add to DB
//         Utilisateur newUser = new Utilisateur(firstName, lastName, email, type);
//         utilisateurDAO.add(newUser);

//         // Optionally clear the fields after adding
//         firstNameField.clear();
//         lastNameField.clear();
//         emailField.clear();
//         typeField.clear();

//         // Refresh the list view
//         refreshUserList();
//     }

//     // Update user action
//     @FXML
//     public void handleUpdateUser(ActionEvent event) {
//         Utilisateur selectedUser = userListView.getSelectionModel().getSelectedItem();
//         if (selectedUser != null) {
//             // Update user with new values from text fields
//             selectedUser.setNom(firstNameField.getText());
//             selectedUser.setPrenom(lastNameField.getText());
//             selectedUser.setEmail(emailField.getText());
//             selectedUser.setType(typeField.getText());

//             // Call DAO to update the user in the database
//             utilisateurDAO.update(selectedUser);

//             // Refresh the list view
//             refreshUserList();
//         } else {
//             System.out.println("No user selected for update.");
//         }
//     }

//     // Delete user action
//     @FXML
//     public void handleDeleteUser(ActionEvent event) {
//         Utilisateur selectedUser = userListView.getSelectionModel().getSelectedItem();
//         if (selectedUser != null) {
//             // Delete user from the database
//             utilisateurDAO.delete(selectedUser.getId());

//             // Refresh the list view
//             refreshUserList();
//         } else {
//             System.out.println("No user selected for deletion.");
//         }
//     }

   

//     // Refresh the user list
//     private void refreshUserList() {
//         userListView.getItems().clear();
//         userListView.getItems().addAll(utilisateurDAO.getAll());
//     }

//     // Initialize method to populate the list view when the controller is initialized
//     @FXML
//     public void initialize() {
//         refreshUserList();  // Populate the ListView with users when the app starts
//     }


//     @FXML
//     public void handleBackToMain(ActionEvent event) {
//     try {
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml")); // Path to your main menu FXML
//         Parent root = loader.load();
        
//         // Get the current stage
//         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
//         // Set the new scene without closing the stage
//         stage.setScene(new Scene(root));
//     } catch (IOException e) {
//         e.printStackTrace();
//     }
// }

  
    
// }









// // package com.example.main;

// // import javafx.event.ActionEvent;
// // import javafx.fxml.FXML;

// // public class UserController {

// //     @FXML
// //     public void handleAddUser(ActionEvent event) {
// //         System.out.println("Adding a new user...");
// //         // Logic to add user
// //     }

// //     @FXML
// //     public void handleBackToMain(ActionEvent event) {
// //         System.out.println("Going back to main menu...");
// //         // Logic to go back to the main menu (you can close the current window and open the main window)
// //     }
// // }
