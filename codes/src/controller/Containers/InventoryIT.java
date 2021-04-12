package controller.Containers;


import controller.Items.Pass;
import controller.Items.PassType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryIT {
    private Inventory inv1;
    private Pass i1;
    private Inventory inv2;


    @Before
    public void setUp() {
        inv1 = new Inventory();
        i1 = new Pass("1", "This is a ball", PassType.A);
        inv1.addItem(i1);
        inv2 = new Inventory();

    }

    @After
    public void tearDown() {
    }

    //test Boîte Noire
    @Test
    public void TestInvVide() {
        assertEquals(0, inv2.getSize());
        assertTrue(inv2.isEmpty());
    }

    //test Boîte Noire
    @Test
    public void TestInvAdd() {
        inv1.addItem(i1);
        assertEquals(1, inv1.getSize());
        assertFalse(inv1.isEmpty());
        assertEquals(inv1.getItem("1"),i1);
    }

    //test Boîte Noire
    @Test
    public void TestInvRemove() {
        inv1.addItem(i1);
        inv1.removeItem("1");
        assertEquals(0, inv1.getSize());
        assertTrue(inv1.isEmpty());
    }

    /* ============================================================ */
    /* ========================= MOVEITEM ========================= */
    /* ============================================================ */

    //test Boîte Blanche
    @Test
    public void testMoveValidItem() {
        inv1.moveItem(i1.getTag(), inv2);
        assertNull(inv1.getItem(i1.getTag()));
        assertEquals(i1, inv2.getItem(i1.getTag()));
    }

    //test Boîte Blanche
    @Test
    public void testMoveInvalidItem() {
        inv1.moveItem("dqs", inv2);
        assertNull(inv1.getItem("dqs"));
        assertNull(inv2.getItem("dqs"));
    }
}
