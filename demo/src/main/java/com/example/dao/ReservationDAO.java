package com.example.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.DatabaseConnection;
import com.example.model.Reservation;


public class ReservationDAO implements GenericDao<Reservation> {

    @Override
    public void add(Reservation entity) {
        String query = "INSERT INTO reservation (id_user, id_event, id_salle, id_terrain, date_reservation) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, entity.getIdUser());
            stmt.setInt(2, entity.getIdEvent());
            stmt.setInt(3, entity.getIdSalle());
            stmt.setInt(4, entity.getIdTerrain());
            stmt.setString(5, entity.getDateReservation()); // Assuming dateReservation is a String
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation get(int id) {
        String query = "SELECT * FROM reservation WHERE id_reservation=?";
        Reservation reservation = null;
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idUser = rs.getInt("id_user");
                    int idEvent = rs.getInt("id_event");
                    int idSalle = rs.getInt("id_salle");
                    int idTerrain = rs.getInt("id_terrain");
                    String dateReservation = rs.getString("date_reservation");
                    reservation = new Reservation(id, idUser, idEvent, idSalle, dateReservation);
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
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_reservation");
                int idUser = rs.getInt("id_user");
                int idEvent = rs.getInt("id_event");
                int idSalle = rs.getInt("id_salle");
                int idTerrain = rs.getInt("id_terrain");
                String dateReservation = rs.getString("date_reservation");
                reservations.add(new Reservation(id, idUser, idEvent, idSalle, dateReservation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void update(Reservation entity) {
        String query = "UPDATE reservation SET id_user=?, id_event=?, id_salle=?, id_terrain=?, date_reservation=? WHERE id_reservation=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, entity.getIdUser());
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
        String query = "DELETE FROM reservation WHERE id_reservation=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
