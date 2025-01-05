package com.example.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Utilisateur;
import com.example.database.DatabaseConnection;


public class UtilisateurDAO implements GenericDao<Utilisateur> {

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> users = new ArrayList<>();
        String query = "SELECT * FROM \"User\"";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false); // Start a transaction

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
            if (con != null) {
                try {
                    con.rollback(); // Rollback in case of an error
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            // Close resources
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
        String query = "INSERT INTO \"User\" (firstname, lastname, email, type) VALUES (?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false); // Start a transaction

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
            if (con != null) {
                try {
                    con.rollback(); // Rollback in case of an error
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public Utilisateur get(int id) {
        String query = "SELECT * FROM \"User\" WHERE id=?";
        Utilisateur user = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false); // Start a transaction

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

            con.commit(); // Commit the transaction
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback in case of an error
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            // Close resources
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
        String query = "UPDATE \"User\" SET firstname=?, lastname=?, email=?, type=? WHERE id=?";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false); // Start a transaction

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

            con.commit(); // Commit the transaction
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback in case of an error
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM \"User\" WHERE id=?";
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projet", "postgres", "lina123");
            con.setAutoCommit(false); // Start a transaction

            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Error deleting user");
            }

            con.commit(); // Commit the transaction
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback in case of an error
                } catch (SQLException ex) {
                    System.out.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.out.println(e.getMessage());
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
