package com.example.model;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String type;
    private String password;

    public Utilisateur(int id , String nom,String prenom,String email,String type , String password) {
        this.id=id;
        this.nom =nom;
        this.prenom= prenom;
        this.email =email;
        this.type =type;
        this.password=password;
    } 

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Utilisateur : id=" + id + ", nom='" + nom + "', prenom='" + prenom + "', email='" + email + "', type='" + type + "' .";
    }
    public void setNom(String nom) {
        this.nom= nom;
    }

    public void setPrenom(String prenom) {
        this.prenom= prenom;
    }

    public void setEmail(String email) {
        this.email =email;
    }

    public void setType(String type) {
        this.type= type;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password= password;
    }
}