package silent_in_space.model.Commands;

import silent_in_space.model.Characters.Attackable;
import silent_in_space.model.Characters.NPC;
import silent_in_space.model.Characters.Player;
import silent_in_space.model.Doors.Door;
import silent_in_space.model.Doors.LockedDoor;
import silent_in_space.model.Items.*;
import silent_in_space.model.Location.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConverterIT {

    private NPC npc;

    private Room room1;

    private Door door1to2;
    private Item tk_item;
    private Item room1_item;
    private Item room2_item;

    private Converter converter;


    @Before
    public void setUp() {
        room1 = new Room(null, 1, "room-test1");
        Room room2 = new Room(null, 2, "room-test2");
        Room room3 = new Room(null, 3, "room-test2");

        door1to2 = new Door("door1to2");
        Door door2to1 = new Door("door2to1");
        LockedDoor door1to3 = new LockedDoor("door1to3", PassType.A);
        Door door3to1 = new Door("door1to3");

        room1.addDoor(door1to2, room2);
        room2.addDoor(door2to1, room1);
        room1.addDoor(door1to3, room3);
        room3.addDoor(door3to1, room1);

        room1_item = new Artefact("Datapad", "a datapad");
        room1.getInventory().addItem(room1_item);
        room2_item = new Artefact("Computer", "a computer");
        room2.getInventory().addItem(room2_item);

        List<Item> npc_inventory = new ArrayList<>();
        npc = new NPC("npc", "an npc", false, true, npc_inventory, room1);

        Player player = new Player(room1, null);
        tk_item = new Artefact("statue", "a statue");
        player.getInventory().addItem(tk_item);

        converter = new Converter(player);
    }

    @After
    public void tearDown() {
    }

    /* ============================================================= */
    /* ================== convertAttackable ======================== */
    /* ============================================================= */

    @Test
    public void testAttackableGoodArgNPC()
    {
        try {
            Attackable attackable = converter.convertAttackable("npc");
            assertEquals(attackable, room1.getActor("npc"));
        } catch (StringRequestUnmatched ignored) {
        }
    }

    @Test
    public void testAttackableGoodArgPlayerHimself()
    {
        try {
            Attackable attackable = converter.convertAttackable("me");
            assertEquals(attackable, room1.getActor("me"));
        } catch (StringRequestUnmatched ignored) {
        }
    }

    @Test
    public void testAttackableBadArg() {
        try {
            converter.convertAttackable("vcdgfef");
            fail();
        } catch (StringRequestUnmatched ignored) {
        }
    }

    /* ============================================================= */
    /* ======================= convertDoor ========================= */
    /* ============================================================= */

    @Test
    public void testDoorGoodArg() {
        try {
            Door d = converter.convertDoor(door1to2.getTag());
            assertEquals(d, room1.getDoor(door1to2.getTag()));
        } catch (StringRequestUnmatched e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoorBadArg() {
        try {
            converter.convertDoor("dfqrbfghreqv");
            fail();
        } catch (StringRequestUnmatched ignored) {
        }
    }

    /* ============================================================= */
    /* ===================== convertItem =========================== */
    /* ============================================================= */

    @Test
    public void testItemGoodArg()
    {
        try {
            // The item can be in the player's room:
            Item i1 = converter.convertItem(room1_item.getTag());
            assertEquals(i1, room1_item);

            // Or in the player's inventory:
            Item i2 = converter.convertItem(tk_item.getTag());
            assertEquals(i2, tk_item);
        } catch (StringRequestUnmatched ignored) {
        }
    }

    @Test
    public void testItemBadArg() {
        try {
            // The object is in another room than in the player's room
            // Or the player misspelled the object's tag::
            converter.convertItem(room2_item.getTag());
            fail();
        } catch (StringRequestUnmatched ignored) {
        }
    }


    /* ============================================================= */
    /* ==================== convertLookable ======================== */
    /* ============================================================= */

    @Test
    public void testLookableGoodArg() {
        try {
            // It can be an item in the player's inventory:
            Lookable l = converter.convertLookable(tk_item.getTag());
            assertEquals(tk_item, l);

            // Or in the room's inventory:
            l = converter.convertLookable(room1_item.getTag());
            assertEquals(room1_item, l);

            // Or an npc in the player's room:
            l = converter.convertLookable(npc.getName());
            assertEquals(npc, l);

            // Or a door in the player's room:
            l = converter.convertLookable(door1to2.getTag());
            assertEquals(door1to2, l);
        } catch (StringRequestUnmatched ignored) {
        }
    }

    @Test
    public void testLookableBadArg() {
        try {
            // It's not an item in the player's inventory
            // or in the room's inventory or an npc in the player's room
            // or a door in the player's room or the player misspelled the lookable's tag:
            converter.convertLookable(room2_item.getTag());
            fail();
        } catch (StringRequestUnmatched ignored) {
        }
    }

    /* ============================================================= */
    /* ====================== convertNPC =========================== */
    /* ============================================================= */

    @Test
    public void testNpcGoodArg() {
        try {
            NPC npc2 = converter.convertNPC(npc.getName());
            assertEquals(npc, npc2);
        } catch (StringRequestUnmatched ignored) {
        }
    }

    @Test
    public void testNpcBadArg() {
        try {
            converter.convertNPC("blablabla");
            fail();
        } catch (StringRequestUnmatched ignored) {
        }
    }

    /* ============================================================= */
    /* =================== convertPlayerItem ======================= */
    /* ============================================================= */

    @Test
    public void testPlayerItemGoodArg()
    {
        try {
            // It must be in the player's inventory:
            Item i2 = converter.convertPlayerItem(tk_item.getTag());
            assertEquals(i2, tk_item);
        } catch (StringRequestUnmatched ignored) {
        }
    }

    @Test
    public void testPlayerItemBadArg() {
        try {
            // The object is a room's inventory
            // Or the player misspelled the object's tag:
            converter.convertPlayerItem(room1_item.getTag());
            fail();
        } catch (StringRequestUnmatched ignored) {
        }
    }

    /* ============================================================= */
    /* ===================== convertUsableOn ======================= */
    /* ============================================================= */

    // Fait la même chose que convertItem() mais cast juste en UsableOn...

    /* ============================================================= */
    /* ===================== convertUsableBy ======================= */
    /* ============================================================= */

    @Test
    public void testconvertUsableByGoodArg() {

        try {
            // It can be an item in the player's inventory:
            UsableBy u = converter.convertUsableBy(tk_item.getTag());
            assertEquals(tk_item, u);

            // Or in the room's inventory:
            u = converter.convertUsableBy(room1_item.getTag());
            assertEquals(room1_item, u);

            // Or an npc in the player's room:
            u = converter.convertUsableBy(npc.getName());
            assertEquals(npc, u);

            // Or a door in the player's room:
            u = converter.convertUsableBy(door1to2.getTag());
            assertEquals(door1to2, u);
        } catch (StringRequestUnmatched ignored) {
            fail();
        }

    }

    @Test
    public void testconvertUsableByBadArg() {
        try {
            // It's not an item in the player's inventory or in the room's inventory
            // or an npc in the player's room :
            converter.convertUsableBy(room2_item.getTag());
            fail();
        } catch (StringRequestUnmatched ignored) {
        }
    }
}



