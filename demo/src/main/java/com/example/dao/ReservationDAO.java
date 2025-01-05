package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Reservation;

public class ReservationDAO implements GenericDao<Reservation> {

    @Override
    public void add(Reservation entity) {
        String query = "INSERT INTO reservation (user_id, event_id , room_id, field_id, reservation_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, entity.getIdUser ());
            stmt.setInt(2, entity.getIdEvent());
            stmt.setInt(3, entity.getIdSalle());
            stmt.setInt(4, entity.getIdTerrain());
            stmt.setString(5, entity.getDateReservation()); 
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation get(int id) {
        String query = "SELECT * FROM reservation WHERE id=?";
        Reservation reservation = null;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idUser  = rs.getInt("user_id");
                    int idEvent = rs.getInt("event_id");
                    int idSalle = rs.getInt("room_id");
                    int idTerrain = rs.getInt("field_id");
                    String dateReservation = rs.getString("reservation_date");
                    reservation = new Reservation(id, idUser , idEvent, idSalle, idTerrain, dateReservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservation";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idUser  = rs.getInt("user_id");
                int idEvent = rs.getInt("event_id");
                int idSalle = rs.getInt("room_id");
                int idTerrain = rs.getInt("field_id");
                String dateReservation = rs.getString("reservation_date");
                reservations.add(new Reservation(id, idUser , idEvent, idSalle, idTerrain, dateReservation));
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
            stmt.setInt(1, entity.getIdUser ());
            stmt.setInt(2, entity.getIdEvent());
            stmt.setInt(3, entity.getIdSalle());
            stmt.setInt(4, entity.getIdTerrain());
            stmt.setString(5, entity.getDateReservation()); // Assuming dateReservation is a String
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
