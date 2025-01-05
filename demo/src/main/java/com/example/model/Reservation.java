package com.example.model;

public class Reservation {
    private int idReservation;
    private int idUser ;
    private int idEvent;
    private int idSalle;
    private int idTerrain;
    private  String dateReservation;

    public Reservation(int idReservation, int idUser, int idEvent, int idSalle, String dateReservation) {
        this.idReservation = idReservation;
        this.idUser=idUser ;
        this.idEvent=idEvent;
        this.idSalle=idSalle;
        this.idTerrain=idTerrain;
        this.dateReservation=dateReservation;
    }

    public int getIdReservation() {
        return this.idReservation;
    }

    public int getIdUser () {
        return this.idUser ;
    }

    public int getIdEvent() {
        return this.idEvent;
    }

    public int getIdSalle() {
        return this.idSalle;
    }

    public int getIdTerrain() {
        return this.idTerrain;
    }

    public String getDateReservation() {
        return this.dateReservation;
    }

    @Override
    public String toString() {
        return "Reservation : idReservation " + idReservation + ",idUser " + idUser  + ", idEvent " + idEvent +
               ", idSalle=" + idSalle + ",idTerrain=" + idTerrain + ",dateReservation" + dateReservation + " .";
    }

    public void setIdUser (int idUser ) {
        this.idUser=idUser ;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent=idEvent;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle=idSalle;
    }

    public void setIdTerrain(int idTerrain) {
        this.idTerrain=idTerrain;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation=dateReservation;
    }
}