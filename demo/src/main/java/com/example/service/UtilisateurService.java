package com.example.service;
import com.example.dao.UtilisateurDAO;
import com.example.model.Utilisateur;
import java.util.List;

public class UtilisateurService {

    private UtilisateurDAO utilisateurDAO;

    // Constructor to initialize the DAO
    public UtilisateurService() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    // Add a new utilisateur
    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.add(utilisateur);
    }

    // Get utilisateur by ID
    public Utilisateur getUtilisateur(int id) {
        return utilisateurDAO.get(id);
    }

    // Get all utilisateurs
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.getAll();
    }

    // Update an utilisateur
    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }

    // Delete an utilisateur by ID
    public void deleteUtilisateur(int id) {
        utilisateurDAO.delete(id);
    }
}
