
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.SalleDAO;
import com.example.model.Salle;

public class SalleDAOTest {

    @Test
    public void testGetAll() {
        SalleDAO salleDAO = new SalleDAO();
      
        List<Salle> salles = salleDAO.getAll();
        
        assertEquals(6, salles.size());
        
        assertEquals("Amphi", salles.get(0).getNomSalle());
        assertEquals(100, salles.get(0).getCapacite());
    }
}
