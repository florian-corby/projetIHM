package model.Items;

import java.io.Serializable;

public class File extends Item implements Serializable {

    private final String CONTENT;

    public File(String tag, String description, String content){
        super(tag, description, true, true);
        this.CONTENT = content;
    }

    public File getCopy()
    {
        return new File(this.getTag(), this.getDescription(), this.CONTENT);
    }

    public String getContent() { return this.CONTENT; }

    @Override
    public void isUsed(UsableBy u) {
        System.out.println(this.CONTENT);
    }
}
