package Items;

import java.io.Serializable;

public class Artefact extends Item implements Serializable
{
    public Artefact(String tag, String description) {
        super(tag, description, true,false);
    }

    @Override
    public void isUsed(UsableBy u)
    {
        this.describe();
    }
}
