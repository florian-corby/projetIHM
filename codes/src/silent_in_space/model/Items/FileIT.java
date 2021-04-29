package silent_in_space.model.Items;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileIT {
    private File f1;

    @Before
    public void setUp()
    {
        //Initialisation d'un File
        f1 = new File("file1", "a", "a");
    }

    @After
    public void tearDown()
    {
    }

    /* ========================================================= */
    /* ======================= GETCOPY ========================= */
    /* ========================================================= */

    //test Boîte Noire
    @Test
    public void testGetCopy()
    {
        //On vérifie que la copie d'un file est parfaite
        File f2 = f1.getCopy();
        assertEquals(f1.getTag(), f2.getTag());
        assertEquals(f1.getDescription(), f2.getDescription());
        assertEquals(f1.getContent(), f2.getContent());
    }

}
