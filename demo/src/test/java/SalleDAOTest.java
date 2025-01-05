
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.SalleDAO;
import com.example.model.Salle;

public class SalleDAOTest {

    @Test
    public void testGetAll() {
        SalleDAO salleDAO = new SalleDAO();
        
        // Call the method to get all salles
        List<Salle> salles = salleDAO.getAll();
        
        // Assert the size of the list (assuming you expect a certain number of salles in the DB)
        assertEquals(1, salles.size()); // Adjust the expected size based on your DB
        
        // Optionally, assert the properties of the first Salle object
        assertEquals("Salle 1", salles.get(0).getNomSalle());
        assertEquals(30, salles.get(0).getCapacite());
    }
}
