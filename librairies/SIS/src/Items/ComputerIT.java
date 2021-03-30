package Items;

import Characters.NPC;
import Location.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ComputerIT {
    private Computer comp;
    private File f1;
    private NPC npc;
    private Room r1;

    @Before
    public void setUp()
    {
        comp = new Computer("testComputer", "testComputer");
        f1 = new File("testFile", "testFile", "testFile");

        List<Item> list = new ArrayList<>();
        r1 = new Room(null, 1, "room-test1");
        npc = new NPC("tester", "", false, true, list, r1);
    }

    @After
    public void tearDown()
    {
    }

    /* ========================================================= */
    /* ======================= ADDFILE ========================= */
    /* ========================================================= */

    //test Boîte Noire
    @Test
    public void testAddFile()
    {
        comp.addFile(f1);
        assertEquals(f1, comp.getFILES().getItem(f1.getTag()));
    }

    /* =========================================================== */
    /* ======================= PRINTFILE ========================= */
    /* =========================================================== */

    //test Boîte Noire
    @Test
    public void testPrintValidFile()
    {
        comp.addFile(f1);
        comp.printFile(f1.getTag(), npc);
        assertEquals(f1.getTag(), (npc.getInventory().getItem(f1.getTag())).getTag());
    }

    //test Boîte Noire
    @Test
    public void testPrintInvalidFile()
    {
        comp.printFile("shuf", npc);
        assertNull(npc.getInventory().getItem(f1.getTag()));
    }
}
