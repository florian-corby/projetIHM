package Characters;

import Items.Item;
import Location.Room;

import java.io.Serializable;
import java.util.List;

public class Umhon extends NPC implements Serializable {
    public Umhon(String name, String description, boolean isHostile, boolean isAlly, List<Item> items, Room r) {
        super(name, description, isHostile, isAlly, items, r);
    }

    @Override
    public void receive(Actor a, String tag) {

        if(tag.equals("doctorLog")) {
            System.out.println("Oh, this is a file from the lab? Thank you sweetheart! Let me take a look at this...\n" +
                    "...Oh. Oh, my." +
                    "I'm... so sorry. Here, have this. I'm going to have a serious talk with my husband tonight. Best of luck.");
            this.give("CaptainCode", a);
            this.setSpeech("I'm going to have a serious talk with my husband tonight. Best of luck.");
            this.setAlly(true);
        }

        else
            super.receive(a, tag);
    }
}
