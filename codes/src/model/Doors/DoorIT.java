package model.Doors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DoorIT
{
    private Door d1;

    @Before
    public void setUp()
    {
        d1 = new Door("door");
    }

    @After
    public void tearDown()
    {
    }

    //test Boîte Blanche
    @Test
    public void testSwitchOpen1()
    {
        //On ouvre:
        d1.open();
        assertTrue(d1.isOpen());

        //On ferme:
        d1.close();
        assertFalse(d1.isOpen());
    }
}
