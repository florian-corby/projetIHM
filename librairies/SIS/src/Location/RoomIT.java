package Location;

import Characters.Player;
import Doors.Door;
import Doors.LockedDoor;
import Items.PassType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomIT
{
    private Door d1;
    private LockedDoor d2;
    private Room r1;
    private Room r2;
    private Player a1;

    @Before
    public void setUp()
    {
        d1 = new Door("door1");
        d2 = new LockedDoor("door2", PassType.A);
        r1 = new Room(null,1,"room-test1");
        r2 = new Room(null,2,"room-test2");
        r1.addDoor(d1, r2);
        r2.addDoor(d2, r1);
        a1= new Player(r1, null);
    }

    @After
    public void tearDown()
    {
    }

    /* ================================================================ */
    /* =============== ADDDOOR, ADDACTOR & REMOVE ACTOR =============== */
    /* ================================================================ */

    //test mouvements entre le pieces
    //test Boîte Noire
    @Test
    public void testAddActor()
    {
        r1.addActor(a1);
        assertEquals(r1.getActor("me"),a1);
        assertTrue(r1.hasActor("me"));
    }

    //test Boîte Blanche
    @Test
    public void testRemoveActor()
    {
        r1.addActor(a1);
        r1.removeActor("me");
        assertNotEquals(r1.getActor("me"),a1);

    }

    //test Boîte Blanche
    @Test
    public void testAddDoor()
    {
        r1.addDoor(d1,r1);
        assertEquals(r1.getDoor("door1"),d1);
    }

    /* ============================================================= */
    /* ========================= GETDOOR =========================== */
    /* ======================= STRING VER. ========================= */
    /* ============================================================= */

    //test Boîte Noire
    @Test
    public void testGetValidDoorString() {
        Door door = r1.getDoor(d1.getTag());
        assertEquals(door, d1);
    }

    //test Boîte Noire
    @Test
    public void testGetInvalidDoorString() {
        Door door = r1.getDoor("cqd");
        assertNull(door);
    }

    /* =========================================================== */
    /* ======================== GETDOOR ========================== */
    /* ======================= ROOM VER. ========================= */
    /* =========================================================== */

    //test Boîte Noire
    @Test
    public void testGetValidDoorRoom() {
        Door door = r1.getDoor(r2);
        assertEquals(door, d1);
    }

    //test Boîte Noire
    @Test
    public void testGetInvalidDoorRoom() {
        Door door = r1.getDoor(r1);
        assertNull(door);
    }

    /* =============================================================== */
    /* ======================= HASLOCKEDDOOR ========================= */
    /* =============================================================== */

    //test Boîte Noire
    @Test
    public void testDoesHaveLockedDoor()
    {
        assertTrue(r2.hasLockedDoor());
    }

    //test Boîte Noire
    @Test
    public void testDoesNotHaveLockedDoor()
    {
        assertFalse(r1.hasLockedDoor());
    }

    /* =============================================================== */
    /* ========================== USEDOOR ============================ */
    /* =============================================================== */

    //test Boîte Noire
    @Test
    public void testRoomActorUseDoor()
    {
        r1.addActor(a1);
        r1.addDoor(d1,r1);
        r1.useDoor(a1,d1);
        assertTrue(r1.hasActor("me"));
    }

}
