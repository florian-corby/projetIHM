package model.Items;

import model.Utils.Pos2D;

import java.io.Serializable;

public class File extends Item implements Serializable {

    private final String CONTENT;

    public File(String tag, String description, String content){
        super(tag, description, new Pos2D(0, 0), true, true);
        this.CONTENT = content;
    }

    public File(String tag, String description, Pos2D pos2D, String content){
        super(tag, description, pos2D, true, true);
        this.CONTENT = content;
    }

    public File getCopy()
    {
        return new File(getTag(), getDescription(), getPos2D(), CONTENT);
    }

    public String getContent() { return this.CONTENT; }

    @Override
    public void isUsed(UsableBy u) {
        System.out.println(this.CONTENT);
    }
}
