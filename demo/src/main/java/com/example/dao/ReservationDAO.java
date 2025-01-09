
package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Reservation;

public class ReservationDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/Projet";
    private static final String USER = "postgres";
    private static final String PASSWORD = "lina123"; 
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reservations.add(new Reservation(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("event_id"),
                    rs.getInt("room_id"),
                    rs.getInt("field_id"),
                    rs.getString("reservation_date").toString()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
    public void add(Reservation reservation) {
        String query = "INSERT INTO reservations (user_id, event_id, room_id, field_id, reservation_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservation.getIdUser());
            stmt.setInt(2, reservation.getIdEvent());
            stmt.setInt(3, reservation.getIdSalle());
            stmt.setInt(4, reservation.getIdTerrain());
            stmt.setDate(5, Date.valueOf(reservation.getDateReservation()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public void updateReservation(Reservation reservation) {
        String query = "UPDATE reservations SET reservation_date = ? WHERE id = ?";
    
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(reservation.getDateReservation())); 
            stmt.setInt(2, reservation.getIdReservation()); 
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteReservation(int reservationId) {
        String query = "DELETE FROM reservations WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
