package model.Items;

import model.Utils.Pos2D;

import java.io.Serializable;

public class Sign extends Item implements Serializable {
    private final String CONTENT;

    public Sign(String tag, String description, String content)
    {
        super(tag, description, new Pos2D(0, 0));
        this.CONTENT = content;
    }

    public Sign(String tag, String description, String content, Pos2D pos2D)
    {
        this(tag, description, content);
        setPos2D(pos2D);
    }

    @Override
    public void describe()
    {
        System.out.println(this.getDescription());
    }

    @Override
    public void isUsed(UsableBy u)
    {
        System.out.println(CONTENT);
    }
}
