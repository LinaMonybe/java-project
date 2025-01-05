package com.example.service;
import java.util.List;

import com.example.dao.ReservationDAO;
import com.example.model.Reservation;

public class ReservationService {

    private ReservationDAO reservationDAO;

    public ReservationService() {
        this.reservationDAO = new ReservationDAO();  // Dependency injection can also be used
    }

    public void addReservation(Reservation reservation) {
        // Business logic for adding a reservation (e.g., validation)
        if (isValidReservation(reservation)) {
            reservationDAO.add(reservation);  // Delegate to DAO
        } else {
            System.out.println("Invalid reservation");
        }
    }

    public void deleteReservation(int reservationId) {
        // Business logic for deleting a reservation
        reservationDAO.delete(reservationId);
    }

    public void updateReservation(Reservation reservation) {
        // Business logic for updating a reservation (e.g., validation)
        if (isValidReservation(reservation)) {
            reservationDAO.update(reservation);
        } else {
            System.out.println("Invalid reservation");
        }
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAll();  // Delegate to DAO
    }

    private boolean isValidReservation(Reservation reservation) {
        // Example validation logic (can be more complex)
        return reservation.getDateReservation() != null;
    }
}
