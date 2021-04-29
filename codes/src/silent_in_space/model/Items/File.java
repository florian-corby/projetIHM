package silent_in_space.model.Items;

import silent_in_space.model.Utils.Scalar2D;

import java.io.Serializable;

public class File extends Item implements Serializable {

    private final String CONTENT;

    public File(String tag, String description, String content){
        super(tag, description, new Scalar2D(0, 0), true, true);
        this.CONTENT = content;
    }

    public File(String tag, String description, Scalar2D scalar2D, String content){
        super(tag, description, scalar2D, true, true);
        this.CONTENT = content;
    }

    public File(String tag, String description, Scalar2D scalar2D, boolean isTakable, boolean isGivable, String content){
        super(tag, description, scalar2D, isTakable, isGivable);
        this.CONTENT = content;
    }

    public File getCopy()
    {
        return new File(getTag(), getDescription(), getScalar2D(), CONTENT);
    }

    public String getContent() { return this.CONTENT; }

    @Override
    public void isUsed(UsableBy u) {
        System.out.println(this.CONTENT);
    }
}
