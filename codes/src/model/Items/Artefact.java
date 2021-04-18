package model.Items;

import model.Utils.Pos2D;

import java.io.Serializable;

public class Artefact extends Item implements Serializable
{
    public Artefact(String tag, String description, Pos2D pos2D) {
        super(tag, description, pos2D, true,false);
    }
    public Artefact(String tag, String description) {
        super(tag, description, new Pos2D(0, 0), true,false);
    }

    @Override
    public void isUsed(UsableBy u)
    {
        this.describe();
    }
}
