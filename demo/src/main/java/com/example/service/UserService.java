package com.example.service;

import java.util.List;

import com.example.dao.UtilisateurDAO;
import com.example.model.Utilisateur;

public class UserService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    // Add a new user with conditions
    public String addUser(Utilisateur user) {
        if (user.getNom() == null || user.getNom().isEmpty()) {
            return "Error: Name cannot be empty.";
        }
        if (!user.getEmail().contains("@")) {
            return "Error: Invalid email format.";
        }
        utilisateurDAO.add(user);
        return "User added successfully!";
    }

    // Get all users
    public List<Utilisateur> getAllUsers() {
        return utilisateurDAO.getAll();
    }

    // Get a user by ID with a condition
    public Utilisateur getUserById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Error: ID must be greater than 0.");
        }
        return utilisateurDAO.get(id);
    }

    // Update user with conditions
    public String updateUser(Utilisateur user) {
        if (user.getId() <= 0) {
            return "Error: Invalid user ID.";
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            return "Error: Invalid email format.";
        }
        utilisateurDAO.update(user);
        return "User updated successfully!";
    }

    // Delete a user by ID with a condition
    public String deleteUserById(int id) {
        if (id <= 0) {
            return "Error: Invalid user ID.";
        }
        utilisateurDAO.delete(id);
        return "User deleted successfully!";
    }
}
