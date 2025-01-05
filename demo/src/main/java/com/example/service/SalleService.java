package com.example.service;

import java.util.List;

import com.example.dao.SalleDAO;
import com.example.model.Salle;

public class SalleService {

    private SalleDAO salleDAO;

    // Constructor to initialize the DAO
    public SalleService() {
        this.salleDAO = new SalleDAO();
    }

    // Add a new salle
    public void addSalle(Salle salle) {
        salleDAO.add(salle);
    }

    // Get salle by ID
    public Salle getSalle(int id) {
        return salleDAO.get(id);
    }

    // Get all salles
    public List<Salle> getAllSalles() {
        return salleDAO.getAll();
    }

    // Update a salle
    public void updateSalle(Salle salle) {
        salleDAO.update(salle);
    }

    // Delete a salle by ID
    public void deleteSalle(int id) {
        salleDAO.delete(id);
    }
}
