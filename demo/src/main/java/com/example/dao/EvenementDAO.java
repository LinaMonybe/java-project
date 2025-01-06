package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Evenement;

public class EvenementDAO implements GenericDao<Evenement> {

    @Override
    public void add(Evenement entity) {
        String query = "INSERT INTO events (name, event_date, description, user_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, entity.getNomEvent());
            stmt.setDate(2, new java.sql.Date(entity.getDateEvent().getTime()));
            stmt.setString(3, entity.getDescription());
            stmt.setInt(4, entity.getIdUser());
            stmt.executeUpdate();

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
    }

    @Override
    public Evenement get(int id) {
        String query = "SELECT * FROM events WHERE id=?";
        Evenement evenement = null;
        
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                Date date = rs.getDate("event_date");
                String description = rs.getString("description");
                int userId = rs.getInt("user_id");
                evenement = new Evenement(id, name, date, description, userId);
            }
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
        return evenement;
    }

    @Override
    public List<Evenement> getAll() {
        String query = "SELECT * FROM events";
        List<Evenement> events = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date date = rs.getDate("event_date");
                String description = rs.getString("description");
                int userId = rs.getInt("user_id");
                events.add(new Evenement(id, name, date, description, userId));
            }
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public void update(Evenement entity) {
        String query = "UPDATE events SET name=?, event_date=?, description=?, user_id=? WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, entity.getNomEvent());
            stmt.setDate(2, new java.sql.Date(entity.getDateEvent().getTime()));
            stmt.setString(3, entity.getDescription());
            stmt.setInt(4, entity.getIdUser());
            stmt.setInt(5, entity.getIdEvent());
            stmt.executeUpdate();

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM events WHERE id=?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
    }
}
