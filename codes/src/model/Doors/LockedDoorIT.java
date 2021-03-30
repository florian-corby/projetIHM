package model.Doors;

import model.Items.Pass;
import model.Items.PassType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LockedDoorIT
{
    private LockedDoor d1;
    private Pass pass;
    private Pass wrongPass;

    @Before
    public void setUp()
    {
        d1 = new LockedDoor("door", PassType.A);
        pass = new Pass("passA", "pass", PassType.A);
        wrongPass = new Pass("passB", "pass", PassType.B);
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
        assertFalse(d1.isOpen());

        //On ferme:
        d1.close();
        assertFalse(d1.isOpen());
    }

    //test Boîte Blanche
    @Test
    public void testSwitchLock()
    {
        //On tente de déverrouiller avec un mauvais pass puis d'ouvrir:
        d1.unlock(wrongPass);
        d1.open();
        assertFalse(d1.isOpen());

        //On tente de déverrouiller avec le bon pass puis d'ouvrir:
        d1.unlock(pass);
        d1.open();
        assertTrue(d1.isOpen());

        //Une porte déverrouillée est déverrouillée pour le reste du jeu...
    }

    //test Boîte Blanche
    @Test
    public void testIsUsedBy()
    {
        //On tente de déverrouiller avec un mauvais pass puis d'ouvrir:
        d1.isUsedBy(wrongPass);
        d1.open();
        assertFalse(d1.isOpen());

        //On tente de déverrouiller avec le bon pass puis d'ouvrir:
        d1.isUsedBy(pass);
        d1.open();
        assertTrue(d1.isOpen());

        //Une porte déverrouillée est déverrouillée pour le reste du jeu...
    }
}
