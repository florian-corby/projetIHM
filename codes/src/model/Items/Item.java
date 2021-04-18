package model.Items;

import model.Commands.Lookable;
import model.Utils.Pos2D;

import java.io.Serializable;

public abstract class Item implements Usable, UsableBy, UsableOn, Serializable, Lookable {

	private final String TAG;
	private final String DESCRIPTION;
	private boolean ISTAKABLE;
	private boolean ISGIVABLE;
	private Pos2D pos2D;

	public Item(String tag, String description, Pos2D pos2D)
	{
		this.TAG = tag;
		this.DESCRIPTION = description;
		this.ISTAKABLE = false;
		this.ISGIVABLE = false;
		this.pos2D = pos2D;
	}

	public Item(String tag, String description, Pos2D pos2D, boolean isTakable, boolean isGivable)
	{
		this(tag, description, pos2D);
		this.ISTAKABLE = isTakable;
		this.ISGIVABLE = isGivable;
	}

	@Override
	public void describe()
	{
		System.out.print(this.getDescription());
	}
	public String getDescription()
	{
		return this.DESCRIPTION;
	}
	public String getTag()
	{
		return this.TAG;
	}
	public Pos2D getPos2D(){ return pos2D; }
	public void setPos2D(Pos2D newPos2D){ pos2D = newPos2D; }
	public boolean isTakable() {
		return this.ISTAKABLE;
	}
	public boolean isGivable() {
		return this.ISGIVABLE;
	}
}