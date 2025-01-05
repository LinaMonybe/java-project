package com.example.model;


public class Salle {
    private int idSalle;
    private String nomSalle;
    private int capacite;

    public Salle(int idSalle, String nomSalle, int capacite) {
        this.idSalle =idSalle;
        this.nomSalle= nomSalle;
        this.capacite=capacite;
    }

    public int getIdSalle() {
        return this.idSalle;
    }

    public String getNomSalle() {
        return this.nomSalle;
    }

    public int getCapacite() {
        return this.capacite;
    }

    @Override
    public String toString() {
        return "Salle : idSalle=" + idSalle + ", nomSalle='" + nomSalle + "', capacite=" + capacite + " .";
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public void setCapacite(int capacite) {
        this.capacite =capacite;
    }
}