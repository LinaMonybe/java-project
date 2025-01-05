import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.ReservationDAO;
import com.example.model.Reservation;

public class ReservationDAOTest {

    @Test
    public void testGetAll() {
        ReservationDAO reservationDAO = new ReservationDAO();
        
        // Call the method to get all reservations
        List<Reservation> reservations = reservationDAO.getAll();
        
        // Assert the size of the list (assuming you expect a certain number of reservations in the DB)
        assertEquals(1, reservations.size()); // Adjust the expected size based on your DB
        
        // Optionally, assert the properties of the first Reservation object
        assertEquals(1, reservations.get(0).getIdReservation()); // Assuming the ID is 1
        assertEquals(1, reservations.get(0).getIdUser ()); // Assuming the user ID is 1
        assertEquals(1, reservations.get(0).getIdEvent()); // Assuming the event ID is 1
        assertEquals(1, reservations.get(0).getIdSalle()); // Assuming the salle ID is 1
        assertEquals("2023-10-01", reservations.get(0).getDateReservation()); // Adjust the date format as needed
    }
}