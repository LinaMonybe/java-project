package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Terrain;

public class TerrainDAO implements GenericDao<Terrain> {

    @Override
    public void add(Terrain entity) {
        String query = "INSERT INTO fields (name, type) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getNomTerrain());
            stmt.setString(2, entity.getType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Terrain get(int id) {
        String query = "SELECT * FROM fields WHERE id=?";
        Terrain terrain = null;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nomTerrain = rs.getString("name");
                    String type = rs.getString("type");
                    terrain = new Terrain(id, nomTerrain, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrain;
    }

    @Override
    public List<Terrain> getAll() {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM fields";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomTerrain = rs.getString("name");
                String type = rs.getString("type");
                terrains.add(new Terrain(id, nomTerrain, type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }

    @Override
    public void update(Terrain entity) {
        String query = "UPDATE fields SET name=?, type=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getNomTerrain());
            stmt.setString(2, entity.getType());
            stmt.setInt(3, entity.getIdTerrain());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM fields WHERE id=?";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}