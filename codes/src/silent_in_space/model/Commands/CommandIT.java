package silent_in_space.model.Commands;

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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CommandIT {

    private Player player;
    private NPC npc;

    private Room room1;
    private Room room2;
    private Room room3;

    private Door door1to2;
    private LockedDoor door1to3;
    private Door door3to1;

    private Item tk_item;
    private File file;
    private Item room_item;
    private List<String> args;


    @Before
    public void setUp() {
        room1 = new Room(null, 1, "room-test1");
        room2 = new Room(null, 2, "room-test2");
        room3 = new Room(null, 3, "room-test2");

        door1to2 = new Door("door1to2");
        Door door2to1 = new Door("door2to1");
        door1to3 = new LockedDoor("door1to3", PassType.A);
        door3to1 = new Door("door1to3");

        room1.addDoor(door1to2, room2);
        room2.addDoor(door2to1, room1);
        room1.addDoor(door1to3, room3);
        room3.addDoor(door3to1, room1);

        room_item = new Artefact("Datapad", "a datapad");
        room1.getInventory().addItem(room_item);

        List<Item> npc_inventory = new ArrayList<>();
        npc = new NPC("npc", "an npc", false, true, npc_inventory, room1);

        player = new Player(room1, null);
        tk_item = new Artefact("statue", "a statue");
        player.getInventory().addItem(tk_item);
        file = new File("file", "a file", "blabla");
        player.getInventory().addItem(file);

        args = new ArrayList<>();
    }

    @After
    public void tearDown() {
    }

    /* ============================================================= */
    /* ======================== Wrong Verb ========================= */
    /* ============================================================= */

    @Test
    public void testWrongVerb() {
        try {
            Command cmd = new Command(player, "blablaincoherentverb", null);
            cmd.exec();
            fail();
        } catch (UnknownVerb e) {
            System.out.println("Enter help for valid verbs");
        }
    }

    /* ============================================================= */
    /* =========================== Back ============================ */
    /* ============================================================= */

    @Test
    public void testBackThroughNormalDoor() {
        player.go(door1to2);
        try {
            Command cmd = new Command(player, "back", null);
            cmd.exec();
            assertEquals(room1, player.getRoom());
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBackThroughLockedDoor() {
        player = new Player(room3, null);

        //The door3to1 isn't a locked door, the player can go to room1.
        //His previous room is then room3:
        player.go(door3to1);

        try {
            Command cmd = new Command(player, "back", args);
            cmd.exec();
            assertNotEquals(room3, player.getRoom());
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    /* ======================================================== */
    /* ========================= DROP ========================= */
    /* ======================================================== */

    @Test
    public void testDropGoodArg() {
        try {
            Command cmd = new Command(player, "drop", Collections.singletonList("statue"));
            cmd.exec();
            assertNull(player.getInventory().getItem("statue"));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDropWrongArg() {
        try {
            Command cmd = new Command(player, "drop", Collections.singletonList("blob"));
            cmd.exec();
            assertEquals(tk_item, player.getInventory().getItem("statue"));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDropNoArg() {
        try {
            Command cmd = new Command(player, "drop", args);
            cmd.exec();
            assertEquals(tk_item, player.getInventory().getItem("statue"));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    /* ======================================================== */
    /* ========================= GIVE ========================= */
    /* ======================================================== */

    @Test
    public void testGiveGivable() {
        args.add(0, file.getTag());
        args.add(1, npc.getName());

        try {
            Command cmd = new Command(player, "give", args);
            cmd.exec();
            assertNull(player.getInventory().getItem(file.getTag()));
            assertEquals(file, npc.getInventory().getItem(file.getTag()));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGiveNotGivable() {
        args.add(0, tk_item.getTag());
        args.add(1, npc.getName());
        try {
            Command cmd = new Command(player, "give", args);
            cmd.exec();
            assertEquals(tk_item, player.getInventory().getItem(tk_item.getTag()));
            assertNull(npc.getInventory().getItem(tk_item.getTag()));
        } catch (UnknownVerb ignored) {
        }
    }

    @Test
    public void testGiveWrongArg() {
        args.add(0, "blob");
        args.add(1, npc.getName());

        try {
            Command cmd = new Command(player, "give", args);
            cmd.exec();
            assertEquals(tk_item, player.getInventory().getItem("statue"));
            assertNull(npc.getInventory().getItem("statue"));
        } catch (UnknownVerb ignored) {
        }
    }

    @Test
    public void testGiveNoArg() {
        try {
            Command cmd = new Command(player, "give", args);
            cmd.exec();
            assertEquals(tk_item, player.getInventory().getItem("statue"));
            assertNull(npc.getInventory().getItem("statue"));
        } catch (UnknownVerb ignored) {
        }
    }

    @Test
    public void testGiveMissingOneArg()
    {
        args.add(0, tk_item.getTag());
        try {
            Command cmd = new Command(player, "give", args);
            cmd.exec();
            assertEquals(tk_item, player.getInventory().getItem("statue"));
            assertNull(npc.getInventory().getItem("statue"));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    /* ======================================================== */
    /* ========================== GO ========================== */
    /* ======================================================== */

    @Test
    public void testGoNormalDoor() {
        args.add(0, door1to2.getTag());

        try {
            Command cmd = new Command(player, "go", args);
            cmd.exec();
            assertEquals(room2, player.getRoom());
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGoLockedDoor() {
        args.add(0, door1to3.getTag());

        try {
            Command cmd = new Command(player, "go", args);
            cmd.exec();
            assertNotEquals(room3, player.getRoom());
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGoWrongArg() {
        args.add(0, "toto");

        try {
            Command cmd = new Command(player, "go", args);
            cmd.exec();
            assertEquals(room1, player.getRoom());
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGoNoArg() {
        try {
            Command cmd = new Command(player, "go", args);
            cmd.exec();
            assertEquals(room1, player.getRoom());
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    /* ======================================================== */
    /* ========================= TAKE ========================= */
    /* ======================================================== */

    @Test
    public void testTakeValidArg() {
        args.add(0, room_item.getTag());

        try {
            Command cmd = new Command(player, "take", args);
            cmd.exec();
            assertNull(room1.getInventory().getItem(room_item.getTag()));
            assertEquals(room_item, player.getInventory().getItem(room_item.getTag()));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTakeWrongArg() {
        args.add(0, "toto");

        try {
            Command cmd = new Command(player, "take", args);
            cmd.exec();
            assertEquals(room_item, room1.getInventory().getItem(room_item.getTag()));
            assertNull(player.getInventory().getItem(room_item.getTag()));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTakeNoArg() {
        try {
            Command cmd = new Command(player, "take", args);
            cmd.exec();
            assertEquals(room_item, room1.getInventory().getItem(room_item.getTag()));
            assertNull(player.getInventory().getItem(room_item.getTag()));
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }
}

