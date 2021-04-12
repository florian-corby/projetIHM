package controller.Commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ParserIT {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testValidVerb() {
        try {
            Parser p = new Parser("go");
            assertEquals(Verb.GO, p.getVerb());
            p = new Parser("GO");
            assertEquals(Verb.GO, p.getVerb());
            p = new Parser("Go");
            assertEquals(Verb.GO, p.getVerb());
            p = new Parser("gO");
            assertEquals(Verb.GO, p.getVerb());
        } catch (UnknownVerb e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidVerb() {
        try {
            new Parser("toto");
            fail();
        } catch (UnknownVerb ignored) {
        }
    }
}


