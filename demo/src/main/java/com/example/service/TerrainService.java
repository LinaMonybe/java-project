package com.example.service;
import com.example.dao.TerrainDAO;
import com.example.model.Terrain;
import java.util.List;

public class TerrainService {

    private TerrainDAO terrainDAO;

    // Constructor to initialize the DAO
    public TerrainService() {
        this.terrainDAO = new TerrainDAO();
    }

    // Add a new terrain
    public void addTerrain(Terrain terrain) {
        terrainDAO.add(terrain);
    }

    // Get terrain by ID
    public Terrain getTerrain(int id) {
        return terrainDAO.get(id);
    }

    // Get all terrains
    public List<Terrain> getAllTerrains() {
        return terrainDAO.getAll();
    }

    // Update a terrain
    public void updateTerrain(Terrain terrain) {
        terrainDAO.update(terrain);
    }

    // Delete a terrain by ID
    public void deleteTerrain(int id) {
        terrainDAO.delete(id);
    }
}
