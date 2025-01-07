package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.database.DatabaseConnection;
import com.example.model.Utilisateur;

public class UtilisateurDAO implements GenericDao<Utilisateur> {

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> users = new ArrayList<>();
        String query = "SELECT * FROM \"User\"";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection con = DatabaseConnection.getConnection(); // Use static connection
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("lastname");
                String prenom = rs.getString("firstname");
                String email = rs.getString("email");
                String type = rs.getString("type");
                Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email, type);
                users.add(utilisateur);
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection();
        } finally {
            closeResources(stmt, rs);
        }
        return users;
    }

    public void add(Utilisateur entity) {
        String query = "INSERT INTO \"User\" (firstname, lastname, email, type) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = null;

        try {
            Connection con = DatabaseConnection.getConnection(); // Use static connection
            stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getNom());
            stmt.setString(2, entity.getPrenom());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getType());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Inserted into user successfully!");
            } else {
                System.out.println("Error adding user");
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection();
        } finally {
            closeResources(stmt, null);
        }
    }

    public Utilisateur get(int id) {
        String query = "SELECT * FROM \"User\" WHERE id=?";
        Utilisateur user = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection con = DatabaseConnection.getConnection(); // Use static connection
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String email = rs.getString("email");
                String type = rs.getString("type");
                user = new Utilisateur(id, fname, lname, email, type);
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection();
        } finally {
            closeResources(stmt, rs);
        }
        return user;
    }

    public void update(Utilisateur entity) {
        String query = "UPDATE \"User\" SET firstname=?, lastname=?, email=?, type=? WHERE id=?";
        PreparedStatement stmt = null;

        try {
            Connection con = DatabaseConnection.getConnection(); // Use static connection
            stmt = con.prepareStatement(query);
            stmt.setString(1, entity.getNom());
            stmt.setString(2, entity.getPrenom());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getType());
            stmt.setInt(5, entity.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Updated user successfully!");
            } else {
                System.out.println("Error updating user");
            }
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackConnection();
        } finally {
            closeResources(stmt, null);
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM \"User\" WHERE id=?";
        PreparedStatement stmt = null;

        try {
            Connection con = DatabaseConnection.getConnection(); // Use static connection
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
            e.printStackTrace();
            rollbackConnection();
        } finally {
            closeResources(stmt, null);
        }
    }

    private void rollbackConnection() {
        try {
            Connection con = DatabaseConnection.getConnection();
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResources(PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
