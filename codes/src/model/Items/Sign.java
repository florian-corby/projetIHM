package model.Items;

import model.Game.Message;
import model.Utils.Scalar2D;

import java.io.Serializable;

public class Sign extends Item implements Serializable {
    private final String CONTENT;

    public Sign(String tag, String description, String content)
    {
        super(tag, description, new Scalar2D(0, 0));
        this.CONTENT = content;
    }

    public Sign(String tag, String description, String content, Scalar2D scalar2D)
    {
        this(tag, description, content);
        setScalar2D(scalar2D);
    }

    @Override
    public void describe()
    {
        Message.sendGameMessage(this.getDescription());
    }

    @Override
    public void isUsed(UsableBy u)
    {
        Message.sendGameMessage(CONTENT);
    }
}
