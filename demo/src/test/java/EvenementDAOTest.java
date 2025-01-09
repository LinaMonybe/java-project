import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.EvenementDAO;
import com.example.model.Evenement;

public class EvenementDAOTest {

    @Test
    public void testGetAll() {
        EvenementDAO evenementDAO = new EvenementDAO();
        
       
        List<Evenement> evenements = evenementDAO.getAll();
        
       
        assertEquals(1, evenements.size()); 
        
        assertEquals("Evenement 1", evenements.get(0).getNomEvent());
        assertEquals("Description de l'événement 1", evenements.get(0).getDescription());
        assertEquals("2023-10-01", evenements.get(0).getDateEvent()); 
    }
}