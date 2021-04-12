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

public class NPCIT {
    private Player player;
    private NPC npc;
    private Room r1;
    private Room r2;
    private File file;


    @Before
    public void setUp() {
        Door d1 = new Door("door1");
        Door d2 = new Door("door2");
        LockedDoor d3 = new LockedDoor("door3", PassType.A);
        r1 = new Room(null, 1, "room-test1");
        r2 = new Room(null, 2, "room-test2");
        Room r3 = new Room(null, 3, "room-test3");
        Pass i1 = new Pass("1", "c'est une balle", PassType.A);
        r1.addDoor(d1, r2);
        r2.addDoor(d2, r1);
        r1.addDoor(d3, r3);
        r1.getInventory().addItem(i1);
        List<Item> list = new ArrayList<>();
        file = new File("file", "", "");
        list.add(file);
        npc = new NPC("npc", "An NPC",false, true, list, r1);
        player = new Player(r1, null);
    }

    @After
    public void tearDown() {
    }

    //Test changement de piéce et vérification que le npc connaît la piece précédente
    // (cas où tout doit fonctionner):
    //test Boîte Blanche
    @Test
    public void testChangeRoom() {

        //On va dans la pièce 2:
        npc.changeRoom(r2);

        //La pièce 1 ne doit plus contenir le joueur:
        assertFalse(r1.hasActor(npc.getName()));

        //La pièce 2 doit contenir le joueur:
        assertTrue(r2.hasActor(npc.getName()));

        //L'acteur doit savoir qu'il est dans la pièce 2:
        assertEquals(r2, npc.getRoom());

        //L'acteur doit savoir que sa pièce précédente est la pièce 1:
        assertEquals(r1, npc.getPreviousRoom());
    }

    /* ======================================================== */
    /* ======================== COMBAT ======================== */
    /* ======================================================== */

    //test Boîte Noire
    @Test
    public void testCombat()
    {
        int a1Hp = npc.getHp();
        int playerHp = player.getHp();

        //Le joueur attaque un NPC allié... Les vies du NPC doivent descendre mais pas ceux
        //du joueur car le NPC ne réplique pas encore. Le NPC doit être devenu neutre:
        player.attack(npc);
        assertNotEquals(a1Hp, npc.getHp());
        assertFalse(npc.isAlly());
        assertEquals(playerHp, player.getHp());

        //Le NPC doit avoir perdu des vies. Il est devenu hostile. Le joueur doit avoir perdu
        //des vies car le NPC réplique:
        a1Hp = npc.getHp();
        playerHp = player.getHp();

        player.attack(npc);
        assertNotEquals(a1Hp, npc.getHp());
        assertTrue(npc.isHostile());
        assertNotEquals(playerHp, player.getHp());

        //Quand le NPC est mort ce-dernier ne perd plus de vies, le joueur non plus:
        while(!(npc.isDead()))
            player.attack(npc);

        a1Hp = npc.getHp();
        playerHp = player.getHp();

        player.attack(npc);
        assertEquals(a1Hp, npc.getHp());
        assertEquals(playerHp, player.getHp());
    }

    /* ======================================================== */
    /* ========================= HEAL ========================= */
    /* ======================================================== */

    //test Boîte Noire
    @Test
    public void testHeal()
    {
        //On fait perdre des hp au NPC:
        npc.isAttacked(player);
        int i = npc.getHp();

        //Si on soigne de 10 alors la vie du NPC a augmenté de 10:
        npc.isHealed(10);
        assertEquals(i + 10, npc.getHp());
    }

    //test Boîte Blanche
    @Test
    public void testExcessHeal()
    {
        //On fait perdre des hp au NPC:
        npc.isAttacked(player);

        //Si on soigne au-delà de la vie maximum possible, le nombre d'hp
        //du npc n'a pas augmenté au-delà de sa vie maximum possible:
        npc.isHealed(npc.getDEFAULT_HP_MAX() + 10);
        assertEquals(npc.getDEFAULT_HP_MAX(), npc.getHp());

    }

    //test Boîte Noire
    @Test
    public void testHealDead()
    {
        while(!(npc.isDead()))
            npc.isAttacked(npc);

        assertTrue(npc.isDead());
        assertEquals(0, npc.getHp());
    }

    /* ======================================================== */
    /* ========================= GIVE ========================= */
    /* ======================================================== */

    //test Boîte Blanche
    @Test
    public void testGiveGoodArg()
    {
        //On vérifie qu'en donnant un item au joueur,
        // il n'existe plus chez le NPC et qu'il existe chez le joueur.
        npc.give(file.getTag(), player);
        assertNull(npc.getInventory().getItem(file.getTag()));
        assertEquals(file, player.getInventory().getItem(file.getTag()));
    }

    //test Boîte Blanche
    @Test
    public void testGiveReceiverDead()
    {
        //On vérifie qu'un Acteur ne peut pas donner d'objet à un autre Acteur mort
        while(!player.isDead()){
            player.isAttacked(npc);
        }

        npc.give(file.getTag(), player);
        assertEquals(file, npc.getInventory().getItem(file.getTag()));
        assertNull(player.getInventory().getItem(file.getTag()));
    }

    //test Boîte Blanche
    @Test
    public void testGiverDead()
    {
        //On vérifie qu'un Acteur mort ne peut pas donner d'objet à un autre Acteur
        while(!npc.isDead()){
            npc.isAttacked(player);
        }

        npc.give(file.getTag(), player);
        assertEquals(file, npc.getInventory().getItem(file.getTag()));
        assertNull(player.getInventory().getItem(file.getTag()));
    }

    //test Boîte Blanche
    @Test
    public void testGiveWrongArg()
    {
        //On vérifie que donner un item invalide cause une erreur
        npc.give("qsfuipd", player);
        assertEquals(file, npc.getInventory().getItem(file.getTag()));
        assertNull(player.getInventory().getItem(file.getTag()));
    }
}
