package silent_in_space.model.Items;

import silent_in_space.model.Utils.Scalar2D;

import java.io.Serializable;

public class Artefact extends Item implements Serializable
{
    public Artefact(String tag, String description, Scalar2D scalar2D) {
        super(tag, description, scalar2D, true,false);
    }
    public Artefact(String tag, String description) {
        super(tag, description, new Scalar2D(0, 0), true,false);
    }

    @Override
    public void isUsed(UsableBy u)
    {
        this.describe();
    }
}
