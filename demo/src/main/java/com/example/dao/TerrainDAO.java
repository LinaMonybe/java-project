package com.example.dao;
import com.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Terrain;

public class TerrainDAO implements GenericDao<Terrain> {

    @Override
    public void add(Terrain entity) {
        String query = "INSERT INTO terrain (nom_terrain, type) VALUES (?, ?)";
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setString(1, entity.getNomTerrain());
            stmt.setString(2, entity.getType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Terrain get(int id) {
        String query = "SELECT * FROM terrain WHERE id_terrain=?";
        Terrain terrain = null;
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nomTerrain = rs.getString("nom_terrain");
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
        String query = "SELECT * FROM terrain";
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id_terrain");
                String nomTerrain = rs.getString("nom_terrain");
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
        String query = "UPDATE terrain SET nom_terrain=?, type=? WHERE id_terrain=?";
        try {
            PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
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
        String query = "DELETE FROM terrain WHERE id_terrain=?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
