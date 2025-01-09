package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Salle;

public class SalleDAO implements GenericDao<Salle> {

    @Override
    public void add(Salle entity) {
        String query = "INSERT INTO rooms (name, capacity) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {

            Class.forName("org.postgresql.Driver");
            stmt.setString(1, entity.getNomSalle());
            stmt.setInt(2, entity.getCapacite());
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Salle get(int id) {
        String query = "SELECT * FROM rooms WHERE id=?";
        Salle salle = null;
        try (Connection connection =DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            Class.forName("org.postgresql.Driver");
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nomSalle = rs.getString("name");
                    int capacite = rs.getInt("capacity");
                    salle = new Salle(id, nomSalle, capacite);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return salle;
    }

    @Override
    public List<Salle> getAll() {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM rooms";
        try (Connection connection =DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            Class.forName("org.postgresql.Driver");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomSalle = rs.getString("name");
                int capacite = rs.getInt("capacity");
                salles.add(new Salle(id, nomSalle, capacite));
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return salles;
    }

    @Override
    public void update(Salle entity) {
        String query = "UPDATE rooms SET name=?, capacity=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {

            Class.forName("org.postgresql.Driver");
            stmt.setString(1, entity.getNomSalle());
            stmt.setInt(2, entity.getCapacite());
            stmt.setInt(3, entity.getIdSalle());
            stmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM rooms WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            Class.forName("org.postgresql.Driver");
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}