package model.Items;

import model.Commands.Lookable;

import java.io.Serializable;

public abstract class Item implements Usable, UsableBy, UsableOn, Serializable, Lookable {

	private final String TAG;
	private final String DESCRIPTION;
	private final boolean ISTAKABLE;
	private final boolean ISGIVABLE;

	public Item(String tag, String description)
	{
		this.TAG = tag;
		this.DESCRIPTION = description;
		this.ISTAKABLE = false;
		this.ISGIVABLE = false;
	}

	public Item(String tag, String description, boolean isTakable, boolean isGivable)
	{
		this.TAG = tag;
		this.DESCRIPTION = description;
		this.ISTAKABLE = isTakable;
		this.ISGIVABLE = isGivable;
	}

	@Override
	public void describe()
	{
		System.out.print(this.getDescription());
	}

	public String getTag()
	{
		return this.TAG;
	}

	public String getDescription()
	{
		return this.DESCRIPTION;
	}

	public boolean isTakable() {
		return this.ISTAKABLE;
	}

	public boolean isGivable() {
		return this.ISGIVABLE;
	}
}