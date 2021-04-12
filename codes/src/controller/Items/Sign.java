package controller.Items;

import java.io.Serializable;

public class Sign extends Item implements Serializable {
    private final String CONTENT;

    public Sign(String tag, String description, String content)
    {
        super(tag, description);
        this.CONTENT = content;
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
