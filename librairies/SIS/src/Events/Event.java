package Events;

import java.io.Serializable;

public class Event implements Serializable {

    private final String TAG;
    private final String DESCRIPTION;
    private final PlayerEvent E;

    public Event(String tag, String description, PlayerEvent e)
    {
        this.TAG = tag;
        this.DESCRIPTION = description;
        this.E = e;
    }

    public String getTag() {
        return TAG;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public PlayerEvent getE() {
        return E;
    }
}
