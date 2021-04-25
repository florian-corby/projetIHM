package model.Items;

import model.Commands.Lookable;
import model.Game.Message;
import model.Utils.Scalar2D;

import java.io.Serializable;

public abstract class Item implements Usable, UsableBy, UsableOn, Serializable, Lookable {

	private final String TAG;
	private final String DESCRIPTION;
	private boolean ISTAKABLE;
	private boolean ISGIVABLE;
	private Scalar2D scalar2D;

	public Item(String tag, String description, Scalar2D scalar2D) {
		this.TAG = tag;
		this.DESCRIPTION = description;
		this.ISTAKABLE = false;
		this.ISGIVABLE = false;
		this.scalar2D = scalar2D;
	}

	public Item(String tag, String description, Scalar2D scalar2D, boolean isTakable, boolean isGivable) {
		this(tag, description, scalar2D);
		this.ISTAKABLE = isTakable;
		this.ISGIVABLE = isGivable;
	}

	@Override
	public void describe()
	{
		Message.sendGameMessage(this.getDescription());
	}
	public String getDescription()
	{
		return this.DESCRIPTION;
	}
	public String getTag()
	{
		return this.TAG;
	}
	public Scalar2D getScalar2D(){ return scalar2D; }
	public void setScalar2D(Scalar2D newScalar2D){ scalar2D = newScalar2D; }
	public boolean isTakable() {
		return this.ISTAKABLE;
	}
	public boolean isGivable() {
		return this.ISGIVABLE;
	}
}