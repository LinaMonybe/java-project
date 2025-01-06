package com.example.main;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.dao.ReservationDAO;
import com.example.model.Reservation;

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

public class ReservationController {

    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Integer> colId;
    @FXML
    private TableColumn<Reservation, Integer> colUserId;
    @FXML
    private TableColumn<Reservation, Integer> colEventId;
    @FXML
    private TableColumn<Reservation, Integer> colRoomId;
    @FXML
    private TableColumn<Reservation, Integer> colFieldId;
    @FXML
    private TableColumn<Reservation, Date> colReservationDate;  // Change to Date type

    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtEventId;
    @FXML
    private TextField txtRoomId;
    @FXML
    private TextField txtFieldId;
    @FXML
    private TextField txtReservationDate;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private ReservationDAO reservationDAO = new ReservationDAO();
    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    private Reservation selectedReservation = null;

    @FXML
    public void initialize() {
        // Set up table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        colEventId.setCellValueFactory(new PropertyValueFactory<>("idEvent"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("idSalle"));
        colFieldId.setCellValueFactory(new PropertyValueFactory<>("idTerrain"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));

        // Load data into the table
        loadReservationData();
    }

    private void loadReservationData() {
        reservationList.clear();
        List<Reservation> reservations = reservationDAO.getAll();
        reservationList.addAll(reservations);
        reservationTable.setItems(reservationList);
    }

    @FXML
    private void handleAddReservation() {
        String userIdStr = txtUserId.getText();
        String eventIdStr = txtEventId.getText();
        String roomIdStr = txtRoomId.getText();
        String fieldIdStr = txtFieldId.getText();
        String reservationDateStr = txtReservationDate.getText();

        if (userIdStr.isEmpty() || eventIdStr.isEmpty() || roomIdStr.isEmpty() || fieldIdStr.isEmpty() || reservationDateStr.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            int eventId = Integer.parseInt(eventIdStr);
            int roomId = Integer.parseInt(roomIdStr);
            int fieldId = Integer.parseInt(fieldIdStr);

            // Convert reservationDate string to java.sql.Date
            java.sql.Date reservationDate = convertStringToDate(reservationDateStr);
            if (reservationDate == null) {
                showAlert("Error", "Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            Reservation newReservation = new Reservation(0, userId, eventId, roomId, fieldId, reservationDate);
            reservationDAO.add(newReservation);

            showAlert("Success", "Reservation added successfully!");
            clearForm();
            loadReservationData();
        } catch (NumberFormatException e) {
            showAlert("Error", "User ID, Event ID, Room ID, and Field ID must be numbers.");
        }
    }

    @FXML
    private void handleUpdateReservation() {
        selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            txtUserId.setText(String.valueOf(selectedReservation.getIdUser()));
            txtEventId.setText(String.valueOf(selectedReservation.getIdEvent()));
            txtRoomId.setText(String.valueOf(selectedReservation.getIdSalle()));
            txtFieldId.setText(String.valueOf(selectedReservation.getIdTerrain()));
            txtReservationDate.setText(selectedReservation.getDateReservation().toString()); // Format date if needed
        } else {
            showAlert("No Selection", "Please select a reservation to update.");
        }
    }

    @FXML
    private void handleSaveReservation() {
        String userIdStr = txtUserId.getText();
        String eventIdStr = txtEventId.getText();
        String roomIdStr = txtRoomId.getText();
        String fieldIdStr = txtFieldId.getText();
        String reservationDateStr = txtReservationDate.getText();

        if (userIdStr.isEmpty() || eventIdStr.isEmpty() || roomIdStr.isEmpty() || fieldIdStr.isEmpty() || reservationDateStr.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            int eventId = Integer.parseInt(eventIdStr);
            int roomId = Integer.parseInt(roomIdStr);
            int fieldId = Integer.parseInt(fieldIdStr);

            // Convert reservationDate string to java.sql.Date
            java.sql.Date reservationDate = convertStringToDate(reservationDateStr);
            if (reservationDate == null) {
                showAlert("Error", "Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            if (selectedReservation == null) {
                // Add new reservation
                Reservation newReservation = new Reservation(0, userId, eventId, roomId, fieldId, reservationDate);
                reservationDAO.add(newReservation);
            } else {
                // Update existing reservation
                selectedReservation.setIdUser(userId);
                selectedReservation.setIdEvent(eventId);
                selectedReservation.setIdSalle(roomId);
                selectedReservation.setIdTerrain(fieldId);
                selectedReservation.setDateReservation(reservationDate);
                reservationDAO.update(selectedReservation);
            }

            showAlert("Success", "Reservation saved successfully!");
            loadReservationData();
            clearForm();
        } catch (NumberFormatException e) {
            showAlert("Error", "User ID, Event ID, Room ID, and Field ID must be numbers.");
        }
    }

    @FXML
    private void handleDeleteReservation() {
        selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            reservationDAO.delete(selectedReservation.getIdReservation());
            loadReservationData();
        } else {
            showAlert("No Selection", "Please select a reservation to delete.");
        }
    }

    private void clearForm() {
        txtUserId.clear();
        txtEventId.clear();
        txtRoomId.clear();
        txtFieldId.clear();
        txtReservationDate.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleRefresh() {
        // Clear the form and reload the reservation data
        clearForm();
        loadReservationData();
        showAlert("Refresh", "Reservation data has been refreshed.");
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

    private java.sql.Date convertStringToDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(dateStr);
            return new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            return null; // Return null if the format is invalid
        }
    }
}
