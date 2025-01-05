package com.example.dao;
import com.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Salle;

public class SalleDAO implements GenericDao<Salle> {

    @Override
    public void add(Salle entity) {
        String query = "INSERT INTO salle (nom_salle, capacite) VALUES (?, ?)";
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setString(1, entity.getNomSalle());
            stmt.setInt(2, entity.getCapacite());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Salle get(int id) {
        String query = "SELECT * FROM salle WHERE id_salle=?";
        Salle salle = null;
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nomSalle = rs.getString("nom_salle");
                    int capacite = rs.getInt("capacite");
                    salle = new Salle(id, nomSalle, capacite);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salle;
    }

    @Override
    public List<Salle> getAll() {
        List<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM salle";
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_salle");
                String nomSalle = rs.getString("nom_salle");
                int capacite = rs.getInt("capacite");
                salles.add(new Salle(id, nomSalle, capacite));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salles;
    }

    @Override
    public void update(Salle entity) {
        String query = "UPDATE salle SET nom_salle=?, capacite=? WHERE id_salle=?";
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setString(1, entity.getNomSalle());
            stmt.setInt(2, entity.getCapacite());
            stmt.setInt(3, entity.getIdSalle());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM salle WHERE id_salle=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
