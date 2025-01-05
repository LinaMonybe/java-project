package com.example.dao;
import com.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Evenement;

public class EvenementDAO implements GenericDao<Evenement> {

    @Override
    public void add(Evenement entity) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO events (name, event_date, description, user_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNomEvent());
            stmt.setDate(2, new java.sql.Date(entity.getDateEvent().getTime()));
            stmt.setString(3, entity.getDescription());
            stmt.setInt(4, entity.getIdUser());
            stmt.executeUpdate();

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback in case of an error
                }
            } catch (SQLException rollbackEx) {
                // Ignore rollback exception
            }
        } finally {
            // Close connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Ignore connection close exception
            }
        }
    }

    @Override
    public Evenement get(int id) {
        Connection conn = null;
        Evenement evenement = null;
        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM events WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                Date date = rs.getDate("event_date");
                String description = rs.getString("description");
                int userId = rs.getInt("user_id");
                evenement = new Evenement(id, name, date, description, userId);
            }
        } catch (SQLException e) {
            // No need to handle exceptions explicitly
        } finally {
            // Close connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Ignore connection close exception
            }
        }
        return evenement;
    }

    @Override
    public List<Evenement> getAll() {
        Connection conn = null;
        List<Evenement> events = new ArrayList<>();
        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM events";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date date = rs.getDate("event_date");
                String description = rs.getString("description");
                int userId = rs.getInt("user_id");
                events.add(new Evenement(id, name, date, description, userId));
            }
        } catch (SQLException e) {
            // No need to handle exceptions explicitly
        } finally {
            // Close connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Ignore connection close exception
            }
        }
        return events;
    }

    @Override
    public void update(Evenement entity) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String query = "UPDATE events SET name=?, event_date=?, description=?, user_id=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, entity.getNomEvent());
            stmt.setDate(2, new java.sql.Date(entity.getDateEvent().getTime()));
            stmt.setString(3, entity.getDescription());
            stmt.setInt(4, entity.getIdUser());
            stmt.setInt(5, entity.getIdEvent());
            stmt.executeUpdate();

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback in case of an error
                }
            } catch (SQLException rollbackEx) {
                // Ignore rollback exception
            }
        } finally {
            // Close connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Ignore connection close exception
            }
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String query = "DELETE FROM events WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback if there's an error
                }
            } catch (SQLException rollbackEx) {
                // Ignore rollback exception
            }
        } finally {
            // Close connection
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Ignore connection close exception
            }
        }
    }
}
