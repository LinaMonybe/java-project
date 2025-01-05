package com.example.model;

import java.util.Date;

public class Evenement {
    private int idEvent;
    private String nomEvent;
    private Date dateEvent;
    private String description;
    private int idUser ;

    public Evenement(int idEvent,String nomEvent,Date dateEvent,String description,int idUser ) {
        this.idEvent =idEvent;
        this.nomEvent =nomEvent;
        this.dateEvent =dateEvent;
        this.description =description;
        this.idUser  =idUser ;
    }

    public int getIdEvent() {
        return this.idEvent;
    }

    public String getNomEvent() {
        return this.nomEvent;
    }

    public Date getDateEvent() {
        return this.dateEvent;
    }

    public String getDescription() {
        return this.description;
    }

    public int getIdUser () {
        return this.idUser ;
    }

    @Override
    public String toString() {
        return "Evenement : idEvent=" + idEvent + ", nomEvent='" + nomEvent + "', dateEvent=" + dateEvent + ", description='" + description + "', idUser =" + idUser  + " . ";
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent=nomEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent=dateEvent;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setIdUser (int idUser ) {
        this.idUser=idUser ;
    }
}