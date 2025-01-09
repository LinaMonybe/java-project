CREATE TABLE users (
    id SERIAL PRIMARY KEY ,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('student', 'professor')),
    password VARCHAR(255) NOT NULL
);

CREATE TABLE events (
id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
name CHAR(100) NOT NULL,
event_date DATE NOT NULL,
description TEXT NOT NULL,
FOREIGN KEY (user_id) REFERENCES users (id));


CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE fields (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    event_id INT NOT NULL,
    room_id INT NOT NULL,
    field_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (event_id) REFERENCES events (id), 
    FOREIGN KEY (room_id) REFERENCES rooms (id),
    FOREIGN KEY (field_id) REFERENCES fields (id)
);
package com.example.main;

import java.util.List;
import com.example.dao.EvenementDAO;
import com.example.dao.UtilisateurDAO;
import com.example.model.Evenement;
import com.example.model.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddRsereveController {

    @FXML
    private Button BackToRoom;

    @FXML
    private Button EventAdd;

    @FXML
    private DatePicker EventDate;

    @FXML
    private Button EventList;

    @FXML
    private TextField EventName;

    @FXML
    private MenuButton EventSelect;

    @FXML
    private DatePicker ReserveDate;

    @FXML
    private MenuButton UsersS;

    @FXML
    private TextArea decription;

    @FXML
    private Button reserve;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
    private Utilisateur selectedUser = null;

    private EvenementDAO evenementDAO = new EvenementDAO();
    private ObservableList<Evenement> eventList = FXCollections.observableArrayList();
    private Evenement selectedEvent = null;

    @FXML
    public void initialize() {
        loadUserData();
        loadEventData();
    }

    private void loadEventData() {
        List<Evenement> events = evenementDAO.getAll();
        EventSelect.getItems().clear();
        eventList.setAll(events);
        for (Evenement event : eventList) {
            MenuItem eventMenuItem = new MenuItem(event.getNomEvent());
            eventMenuItem.setOnAction(eventAction -> {
                selectedEvent = event; // Update selected event
                EventSelect.setText(event.getNomEvent()); // Show selected event's name in the MenuButton
            });
            EventSelect.getItems().add(eventMenuItem);
        }
    }

    private void loadUserData() {
        List<Utilisateur> users = utilisateurDAO.getAll();
        UsersS.getItems().clear();
        userList.setAll(users);
        for (Utilisateur user : userList) {
            MenuItem userMenuItem = new MenuItem(user.getNom());
            userMenuItem.setOnAction(userEvent -> {
                selectedUser = user; // Update selected user
                UsersS.setText(user.getNom()); // Show selected user's name in the MenuButton
            });
            UsersS.getItems().add(userMenuItem);
        }
    }

    @FXML
    void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/salle.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void BackToList(ActionEvent event){
       
    }

    // @FXML
    // void addnewEvent(ActionEvent event) throws IOException {
    //     String name = EventName.getText().trim();
    //     LocalDate reserveDate = ReserveDate.getValue();
    //     String description = decription.getText().trim();

    //     // Validate input
    //     if (selectedUser == null) {
    //         System.out.println("Please select a user.");
    //         return;
    //     }

    //     if (name.isEmpty()) {
    //         System.out.println("Event name cannot be empty.");
    //         return;
    //     }

    //     if (reserveDate == null) {
    //         System.out.println("Reserve date must be selected.");
    //         return;
    //     }

    //     if (description.isEmpty()) {
    //         System.out.println("Description cannot be empty.");
    //         return;
    //     }

    //     // Create a new event
    //     Evenement newEvent = new Evenement(0, name, reserveDate.toString(), description, selectedUser.getId());
    //     evenementDAO.add(newEvent);

    //     // Confirmation
    //     System.out.println("Event added successfully: " + name);
    // }

    @FXML
    void addnewReseve(ActionEvent event) {
        // Implement logic for reservations if needed
    }
}

//     @FXML
//     private TableView<Evenement> evenementTable;
//     @FXML
//     private TableColumn<Evenement, Integer> colEventId;
//     @FXML
//     private TableColumn<Evenement, String> colEventName;
//     @FXML
//     private TableColumn<Evenement, Date> colEventDate;
//     @FXML
//     private TableColumn<Evenement, String> colEventDescription;
//     @FXML
//     private TableColumn<Evenement, Integer> colEventUserId;

//     @FXML
//     private TextField txtEventName;
//     @FXML
//     private TextField txtEventDate;
//     @FXML
//     private TextField txtEventDescription;
//     @FXML
//     private TextField txtUserId;

//     @FXML
//     private Button btnSave;
   
//     private EvenementDAO evenementDAO = new EvenementDAO();
//     private ObservableList<Evenement> evenementList = FXCollections.observableArrayList();
//     private Evenement selectedEvenement = null;

//     @FXML
//     public void initialize() {
//         // Set up table columns
//         colEventId.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
//         colEventName.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
//         colEventDate.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
//         colEventDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
//         colEventUserId.setCellValueFactory(new PropertyValueFactory<>("idUser"));

//         // Load data into the table
//         loadEvenementData();
//     }

//     private void loadEvenementData() {
//         evenementList.clear();
//         evenementList.addAll(evenementDAO.getAll());
//         evenementTable.setItems(evenementList);
//     }

//    @FXML
//     private void handleAddEvenement() {
//         String nomEvent = txtEventName.getText();
//         String eventDateStr = txtEventDate.getText();
//         String description = txtEventDescription.getText();
//         String userIdStr = txtUserId.getText();

//         if (nomEvent.isEmpty() || eventDateStr.isEmpty() || description.isEmpty() || userIdStr.isEmpty()) {
//             showAlert("Error", "Please fill in all fields.");
//             return;
//         }

//         try {
//             int idUser = Integer.parseInt(userIdStr);
//              // Parse date correctly
            
//             // Create a new Evenement object
//             Evenement newEvenement = new Evenement(0, idUser,nomEvent,, description);

//             // Add the new event to the database
//             evenementDAO.add(newEvenement);

//             showAlert("Success", "Event added successfully!");
            
//             // Optionally, clear fields after submission
//             clearForm();
//         } catch (Exception e) {
//             showAlert("Error", "Invalid input data.");
//         }
//     }

//     @FXML
//     private void handleUpdateEvenement() {
//         selectedEvenement = evenementTable.getSelectionModel().getSelectedItem();
//         if (selectedEvenement != null) {
//             txtEventName.setText(selectedEvenement.getNomEvent());
//             txtEventDate.setText(selectedEvenement.getDateEvent().toString());
//             txtEventDescription.setText(selectedEvenement.getDescription());
//             txtUserId.setText(String.valueOf(selectedEvenement.getIdUser()));
//         } else {
//             showAlert("No Selection", "Please select an event to update.");
//         }
//     }

//     @FXML
//     private void handleDeleteEvenement() {
//         selectedEvenement = evenementTable.getSelectionModel().getSelectedItem();
//         if (selectedEvenement != null) {
//             evenementDAO.delete(selectedEvenement.getIdEvent()); // Make sure delete uses the idEvent
//             loadEvenementData(); // Reload the data to reflect the changes
//         } else {
//             showAlert("No Selection", "Please select an event to delete.");
//         }
//     }

//     @FXML
//     private void handleSaveEvenement() {
//         String nomEvent = txtEventName.getText();
//         String eventDate = txtEventDate.getText();
//         String description = txtEventDescription.getText();
//         String userId = txtUserId.getText();

//         if (validateInput(nomEvent, eventDate, description, userId)) {
//             if (selectedEvenement == null) {
//                 // Add new event
//                 Evenement newEvenement = new Evenement(0, nomEvent, new Date(eventDate), description, Integer.parseInt(userId));
//                 evenementDAO.add(newEvenement);
//             } else {
//                 // Update existing event
//                 selectedEvenement.setNomEvent(nomEvent);
//                 selectedEvenement.setDateEvent(new Date(eventDate));
//                 selectedEvenement.setDescription(description);
//                 selectedEvenement.setIdUser(Integer.parseInt(userId));
//                 evenementDAO.update(selectedEvenement);
//             }
//             loadEvenementData();
//             clearForm();
//         }
//     }

//     @FXML
//     private void handleRefresh() {
//         loadEvenementData();
//     }

//     private void clearForm() {
//         txtEventName.clear();
//         txtEventDate.clear();
//         txtEventDescription.clear();
//         txtUserId.clear();
//     }

//     private boolean validateInput(String name, String date, String description, String userId) {
//         if (name.isEmpty() || date.isEmpty() || description.isEmpty() || userId.isEmpty()) {
//             showAlert("Validation Error", "All fields are required.");
//             return false;
//         }
//         return true;
//     }

//     private void showAlert(String title, String content) {
//         Alert alert = new Alert(Alert.AlertType.WARNING);
//         alert.setTitle(title);
//         alert.setContentText(content);
//         alert.showAndWait();
//     }

//     @FXML
//     public void handleBack(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml")); // Ensure path is correct
//             Parent root = loader.load();

//             // Get the current stage
//             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

//             // Set the new scene without closing the stage
//             stage.setScene(new Scene(root));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
