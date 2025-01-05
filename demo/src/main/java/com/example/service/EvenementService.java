package com.example.service;

import com.example.dao.EvenementDAO;
import com.example.model.Evenement;
import java.util.List;

public class EvenementService {

    private EvenementDAO evenementDAO;

    // Constructor to initialize the DAO
    public EvenementService() {
        this.evenementDAO = new EvenementDAO();
    }

    // Add a new evenement
    public void addEvenement(Evenement evenement) {
        evenementDAO.add(evenement);
    }

    // Get evenement by ID
    public Evenement getEvenement(int id) {
        return evenementDAO.get(id);
    }

    // Get all evenements
    public List<Evenement> getAllEvenements() {
        return evenementDAO.getAll();
    }

    // Update an evenement
    public void updateEvenement(Evenement evenement) {
        evenementDAO.update(evenement);
    }

    // Delete an evenement by ID
    public void deleteEvenement(int id) {
        evenementDAO.delete(id);
    }
}
