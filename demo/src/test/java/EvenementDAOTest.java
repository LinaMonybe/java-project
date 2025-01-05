import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.EvenementDAO;
import com.example.model.Evenement;

public class EvenementDAOTest {

    @Test
    public void testGetAll() {
        EvenementDAO evenementDAO = new EvenementDAO();
        
        // Call the method to get all evenements
        List<Evenement> evenements = evenementDAO.getAll();
        
        // Assert the size of the list (assuming you expect a certain number of evenements in the DB)
        assertEquals(1, evenements.size()); // Adjust the expected size based on your DB
        
        // Optionally, assert the properties of the first Evenement object
        assertEquals("Evenement 1", evenements.get(0).getNomEvent());
        assertEquals("Description de l'événement 1", evenements.get(0).getDescription());
        assertEquals("2023-10-01", evenements.get(0).getDateEvent()); // Adjust the date format as needed
    }
}