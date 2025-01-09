package com.example.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Utilisateur;


public class UtilisateurDAO implements GenericDao<Utilisateur> {

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("lastname");
                String prenom = rs.getString("firstname");
                String email = rs.getString("email");
                String type = rs.getString("type");
                String password = rs.getString("password");
                Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, type,password);
                users.add(utilisateur);
            }

            con.commit(); 
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); 
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return users;
    }

    public void add(Utilisateur entity) {
        String query = "INSERT INTO users (firstname, lastname, email, type,password) VALUES (?, ?, ?, ?,?)";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getNom());
            stmt.setString(2, entity.getPrenom());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getType());
            stmt.setString(5, entity.getPassword());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Inserted into user successfully!");
            } else {
                System.out.println("Error adding user");
            }

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); 
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public Utilisateur get(int id) {
        String query = "SELECT * FROM users WHERE id=?";
        Utilisateur user = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
    
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String email = rs.getString("email");
                String type = rs.getString("type");
                String password = rs.getString("password");
                user = new Utilisateur(id, fname, lname, email, type, password);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return user;
    }

    public void update(Utilisateur entity) {
        String query = "UPDATE users SET firstname=?, lastname=?, email=?, type=? , password=? WHERE id=?";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false); 
            stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getNom());
            stmt.setString(2, entity.getPrenom());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getType());
            stmt.setInt(6, entity.getId());
            stmt.setString(5, entity.getPassword());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Updated user successfully!");
            } else {
                System.out.println("Error updating user");
            }
            con.commit(); 
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM users WHERE id=?";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false);
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Error deleting user");
            }
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public Utilisateur validateUser(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            stmt = con.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("lastname");
                String prenom = rs.getString("firstname");
                String type = rs.getString("type");
                String dbPassword = rs.getString("password");
    
              
                return new Utilisateur(id, nom, prenom, email, type, dbPassword);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred during user validation: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }
    
}
