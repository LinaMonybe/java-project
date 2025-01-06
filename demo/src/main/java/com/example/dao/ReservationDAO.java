package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;  // Import the Date class

import com.example.model.Reservation;

public class ReservationDAO implements GenericDao<Reservation> {

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void add(Reservation entity) {
        String query = "INSERT INTO reservation (user_id, event_id, room_id, field_id, reservation_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, entity.getIdUser());
            stmt.setInt(2, entity.getIdEvent());
            stmt.setInt(3, entity.getIdSalle());
            stmt.setInt(4, entity.getIdTerrain());
            stmt.setDate(5, (Date) entity.getDateReservation());  // Use Date here
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public Reservation get(int id) {
        String query = "SELECT * FROM reservation WHERE id=?";
        Reservation reservation = null;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idUser = rs.getInt("user_id");
                    int idEvent = rs.getInt("event_id");
                    int idSalle = rs.getInt("room_id");
                    int idTerrain = rs.getInt("field_id");
                    Date dateReservation = rs.getDate("reservation_date");  // Use Date here
                    reservation = new Reservation(id, idUser, idEvent, idSalle, idTerrain, dateReservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idUser = rs.getInt("user_id");
                int idEvent = rs.getInt("event_id");
                int idSalle = rs.getInt("room_id");
                int idTerrain = rs.getInt("field_id");
                Date dateReservation = rs.getDate("reservation_date");  // Use Date here
                reservations.add(new Reservation(id, idUser, idEvent, idSalle, idTerrain, dateReservation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void update(Reservation entity) {
        String query = "UPDATE reservation SET user_id=?, event_id=?, room_id=?, field_id=?, reservation_date=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, entity.getIdUser());
            stmt.setInt(2, entity.getIdEvent());
            stmt.setInt(3, entity.getIdSalle());
            stmt.setInt(4, entity.getIdTerrain());
            stmt.setDate(5, (Date) entity.getDateReservation());  // Use Date here
            stmt.setInt(6, entity.getIdReservation());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM reservation WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
