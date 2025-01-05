import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.TerrainDAO;
import com.example.model.Terrain;

public class TerrainDAOTest {

    @Test
    public void testGetAll() {
        TerrainDAO terrainDAO = new TerrainDAO();
        
        // Call the method to get all terrains
        List<Terrain> terrains = terrainDAO.getAll();
        
        // Assert the size of the list (assuming you expect a certain number of terrains in the DB)
        assertEquals(1, terrains.size()); // Adjust the expected size based on your DB
        
        // Optionally, assert the properties of the first Terrain object
        assertEquals("Terrain 1", terrains.get(0).getNomTerrain());
        assertEquals("Type A", terrains.get(0).getType());
    }
}