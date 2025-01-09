import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.dao.TerrainDAO;
import com.example.model.Terrain;

public class TerrainDAOTest {

    @Test
    public void testGetAll() {
        TerrainDAO terrainDAO = new TerrainDAO();
        
        List<Terrain> terrains = terrainDAO.getAll();
        
        assertEquals(1, terrains.size()); 
        
        assertEquals("Terrain 1", terrains.get(0).getNomTerrain());
        assertEquals("Type A", terrains.get(0).getType());
    }
}