import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.UtilisateurDAO;
import com.example.model.Utilisateur;

public class UtilisateurDAOTest {

    @Test
    public void testGetAll() {
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        
        // Call the method to get all utilisateurs
        List<Utilisateur> utilisateurs = utilisateurDAO.getAll();
        
        // Assert the properties of the first Utilisateur object
        assertEquals(1, utilisateurs.get(0).getId());
        assertEquals("Lina", utilisateurs.get(0).getPrenom());
        assertEquals("Monybe", utilisateurs.get(0).getNom());
        assertEquals("lina@gmail.com", utilisateurs.get(0).getEmail());
        assertEquals("Student", utilisateurs.get(0).getType());
    }
}
