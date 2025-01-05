package com.example.model;

public class Terrain {
    private int idTerrain;
    private String nomTerrain;
    private String type;

    public Terrain(int idTerrain,String nomTerrain,String type) {
        this.idTerrain=idTerrain;
        this.nomTerrain=nomTerrain;
        this.type=type;
    }

    public int getIdTerrain() {
        return this.idTerrain;
    }

    public String getNomTerrain() {
        return this.nomTerrain;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Terrain : idTerrain" + idTerrain + ",nomTerrain='" + nomTerrain + "', type='" + type + "' .";
    }

    public void setNomTerrain(String nomTerrain) {
        this.nomTerrain=nomTerrain;
    }

    public void setType(String type) {
        this.type = type;
    }
}