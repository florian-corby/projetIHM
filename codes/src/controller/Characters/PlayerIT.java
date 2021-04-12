package controller.Characters;

import controller.Doors.Door;
import controller.Doors.LockedDoor;
import controller.Items.File;
import controller.Items.Item;
import controller.Items.Pass;
import controller.Items.PassType;
import controller.Location.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerIT
{
    private Door d1;
    private LockedDoor d3;
    private Door d6;

    private Room r1;
    private Room r2;
    private Room r3;
    private Room r4;

    private Player player;
    private Player player_emptyInv;
    private Pass passA;
    private File file;

    private NPC npc;



    @Before
    public void setUp()
    {
        //Création des pièces:
        r1 = new Room(null,1,"room-test1");
        r2 = new Room(null,2,"room-test2");
        r3 = new Room(null,3,"room-test3");
        r4 = new Room(null,4,"room-test4");

        //Création des portes:
        d1 = new Door("door1");
        Door d2 = new Door("door2");
        d3 = new LockedDoor("door3", PassType.A);
        Door d4 = new LockedDoor("door4", PassType.B);
        LockedDoor d5 = new LockedDoor("door5", PassType.C);
        d6 = new Door("door6");

        //Connexion des pièces entre elles:
        r1.addDoor(d1,r2);
        r1.addDoor(d3,r3);
        r1.addDoor(d6, r4);
        r2.addDoor(d2,r1);
        r3.addDoor(d4, r1);
        r4.addDoor(d5, r1);
        player = new Player(r1, null);
        file = new File("file", "", "");
        player.getInventory().addItem(file);

        player_emptyInv = new Player(r1, null);

        //Enrichissement des pièces:
        passA = new Pass("passA","This is a pass", PassType.A);
        r1.getInventory().addItem(passA);

        //Définition d'un NPC
        List<Item> list = new ArrayList<>();
        npc = new NPC("npc", "Toto", false, true, list, r1);
    }

    @After
    public void tearDown()
    {
    }

    /* ====================================================== */
    /* ======================= BACK ========================= */
    /* ====================================================== */

    //On tente de revenir dans la pièce précédente
    //alors qu'il n'y a pas de pièce précédente:
    //test Boîte Noire
    @Test
    public void testBackInit()
    {
        player.back();
        assertEquals(r1, player.getRoom());
    }

    //On tente de revenir avec une porte normale:
    //test Boîte Noire
    @Test
    public void testBackNormal()
    {
        player.go(d1);
        assertEquals(r2, player.getRoom());

        player.back();
        assertEquals(r1, player.getRoom());
    }

    //On tente de revenir avec une porte verrouillée:
    //test Boîte Noire
    @Test
    public void testBackLocked()
    {
        player.go(d6);
        assertEquals(r4, player.getRoom());

        player.back();
        assertEquals(r4, player.getRoom());
    }

    /* ======================================================== */
    /* ======================== COMBAT ======================== */
    /* ======================================================== */

    //test Boîte Blanche
    @Test
    public void testCombat()
    {
        int i=player.getHp();
        System.out.print(player.getHp()+"\n");
        player.isAttacked(player);
        System.out.print(player.getHp());
        assertNotEquals(i,player.getHp());

    }

    /* ======================================================== */
    /* ========================= GIVE ========================= */
    /* ======================================================== */

    //test Boîte Blanche
    @Test
    public void testGiveIsGivable()
    {
        //On test si un joueur donne bien un objet, quand il est donnable
        player.give(file.getTag(), npc);
        assertEquals(file, npc.getInventory().getItem(file.getTag()));
        assertNull(player.getInventory().getItem(file.getTag()));
    }

    //test Boîte Blanche
    @Test
    public void testGiveIsNotGivable()
    {
        //On test si un joueur donne bien un objet, quand il n'est pas donnable
        player.take(passA);
        player.give(passA.getTag(), npc);
        assertEquals(passA, player.getInventory().getItem(passA.getTag()));
        assertNull(npc.getInventory().getItem(passA.getTag()));
    }

    //test Boîte Blanche
    @Test
    public void testGiveWrongArg()
    {
        //On test si un joueur ne donne pas un item s'il ne l'a pas
        player.give("fhsiohfsiofhio", npc);
        assertNull(npc.getInventory().getItem("fhsiohfsiofhio"));
        assertNull(player.getInventory().getItem("fhsiohfsiofhio"));
    }

    /* ====================================================== */
    /* ========================= GO ========================= */
    /* ====================================================== */

    //test Boîte Blanche
    @Test
    public void testGoNormal()
    {
        Room r = player.getRoom();
        //On va dans un autre piece:
        player.go(d1);

        //Le joueur doit se souvenir de la pièce précédente et avoir changé de pièce:
        assertEquals(r2, player.getRoom());
        assertEquals(r1, player.getPreviousRoom());

        //La pièce précédente ne doit plus contenir le joueur:
        assertFalse(r.hasActor(player.getName()));

        //La pièce où est allé le joueur doit contenir le joueur:
        assertTrue(r2.hasActor(player.getName()));
    }


    //test Boîte Noire
    @Test
    public void testGoLocked()
    {
        //On veut aller dans une piece mais on ne peut pas:
        player.go(d3);
        assertEquals(r1,player.getRoom());
    }

    /* ======================================================== */
    /* ========================= HEAL ========================= */
    /* ======================================================== */

    //test Boîte Noire
    @Test
    public void testHeal()
    {
        player.isAttacked(player);//vie perdu 20
        int i=player.getHp();
        player.isHealed(10);
        assertNotEquals(i,player.getHp());

    }

    //test Boîte Blanche
    @Test
    public void testExcessHeal()
    {
        player.isAttacked(player);//vie perdu 20
        player.isHealed(200);
        assertTrue(player.getDEFAULT_HP_MAX()>=player.getHp());

    }


    /* ======================================================== */
    /* ======================= TAKE/DROP ====================== */
    /* ======================================================== */

    //test Boîte Blanche
    @Test
    public void testItemPickup()
    {
        assertFalse(player_emptyInv.getRoom().getInventory().isEmpty());
        assertTrue(player_emptyInv.getInventory().isEmpty());
        player_emptyInv.take(passA);
        assertFalse(player_emptyInv.getInventory().isEmpty());
        assertTrue(player_emptyInv.getRoom().getInventory().isEmpty());
    }

    //test Boîte Blanche
    @Test
    public void testItemDrop()
    {
        player_emptyInv.take(passA);
        player_emptyInv.drop(passA);
        assertFalse(player_emptyInv.getRoom().getInventory().isEmpty());
        assertTrue(player_emptyInv.getInventory().isEmpty());
    }


    /* ======================================================== */
    /* ========================== USE ========================= */
    /* ======================================================== */

    //test Boîte Blanche
    @Test
    public void testUsePassOnLocked()
    {
        player.take(passA);
        player.use(passA,d3);
        player.go(d3);
        assertEquals(r3,player.getRoom());
    }
}
