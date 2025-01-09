

package com.example.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ReservationController {

    @FXML
    private Button btnChange;
    @FXML
    private DatePicker DateReserve;

    @FXML
    private Button back;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Reservation, String> colEvent;

    @FXML
    private TableColumn<Reservation, String> colField;

    @FXML
    private TableColumn<Reservation, String> colReservationDate;

    @FXML
    private TableColumn<Reservation, String> colRoom;

    @FXML
    private TableColumn<Reservation, String> colUser;

    @FXML
    private TableView<Reservation> reservationTable;

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private final ObservableList<Reservation> reserveList = FXCollections.observableArrayList();
    private Reservation selectedReservation = null;

    @FXML
    private String getUserName(int userId) {
        String userName = "";
        String query = "SELECT firstname || ' ' || lastname AS full_name FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userName = rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userName;
    }

    @FXML
    private String getEventName(int eventId) {
        String eventName = "";
        String query = "SELECT name FROM events WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    eventName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventName;
    }

    @FXML
    private String getRoomName(int roomId) {
        String roomName = "";
        String query = "SELECT name FROM rooms WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, roomId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    roomName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomName;
    }

    @FXML
    private String getFieldName(int fieldId) {
        String fieldName = "";
        String query = "SELECT name FROM fields WHERE id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, fieldId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fieldName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fieldName;
    }

    @FXML
    public void initialize() {
        // will set up table col
        colUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colEvent.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("roomName"));
        colField.setCellValueFactory(new PropertyValueFactory<>("fieldName"));
        colReservationDate.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        // will looad data into the table
        loadReservationData();
    }

    @FXML
    public void loadReservationData() {
        reserveList.clear();
        List<Reservation> reservations = reservationDAO.getAllReservations(); 
        for (Reservation reservation : reservations) {
            reservation.setUserName(getUserName(reservation.getIdUser()));
            reservation.setEventName(getEventName(reservation.getIdEvent()));
            reservation.setRoomName(getRoomName(reservation.getIdSalle()));
            reservation.setFieldName(getFieldName(reservation.getIdTerrain()));
        }

        reserveList.addAll(reservations);
        reservationTable.setItems(reserveList);
    }

    @FXML
    void backToMain(ActionEvent event) {
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
    void handleDeleteReservation(ActionEvent event) {
        Reservation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            reservationDAO.deleteReservation(selected.getIdReservation());
            loadReservationData();
        }
    }
    // @FXML
    // void handleUpdateReservation(ActionEvent event) {
    //     selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
    //     if (selectedReservation != null) {
    //          String dateReservation = selectedReservation.getDateReservation();
    //          if (dateReservation != null && !dateReservation.isEmpty()) {
    //              // Extract only the date part (in case it's a full datetime string)
    //              String dateOnly = dateReservation.split("T")[0];  // Split by 'T' and take the date part
    //              LocalDate localDate = LocalDate.parse(dateOnly);  // Parse it into LocalDate
    //              DateReserve.setValue(localDate);  // Set the value of the DatePicker
    //          }
    //     }
    // }
    
    private void clearForm() {
       DateReserve.setValue(null);
    }


    @FXML
    void handleUpdateDate(ActionEvent event) {
        String date = DateReserve.getValue().toString();
        if (selectedReservation != null) {
            selectedReservation.setDateReservation(date);
            reservationDAO.updateReservation(selectedReservation);
        }
        loadReservationData();
         clearForm();
    }
}
