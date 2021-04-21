package model.Doors;

import model.Game.Message;
import model.Items.*;
import model.Utils.Scalar2D;

import java.io.Serializable;

public class LockedDoor extends Door implements Unlockable, Serializable {

	private final PassType PASSTYPE;
	private boolean isLocked;

	public LockedDoor(String tag, PassType p)
	{
		super(tag);
		this.PASSTYPE = p;
		this.isLocked = true;
	}

	public LockedDoor(String tag, PassType p, Scalar2D scalar2D)
	{
		super(tag, scalar2D);
		this.PASSTYPE = p;
		this.isLocked = true;
	}

	public void describe()
	{
		super.describe();
		if(this.isLocked)
			Message.sendGameMessage("This door is locked! Some kind of letter " + this.PASSTYPE.toString() + " is written on it...");

		else
			Message.sendGameMessage("This door is unlocked!");
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void open()
	{
		if(!isLocked)
			super.open();

		else
			Message.sendGameMessage("This door is locked! A letter " + this.PASSTYPE.toString() + " is written on it...");
	}

	@Override
	public void unlock(Pass p)
	{
		if(this.PASSTYPE == p.getPassType())
		{
			this.isLocked = false;
			Message.sendGameMessage("You have unlocked the " + this.getTag() + " !");
		}

		else
		{
			Message.sendGameMessage("You can't unlock the " + this.getTag() + " with this pass.");
		}
	}

	@Override
	public void isUsedBy(UsableOn u)
	{
		if(u instanceof Pass)
		{
			Pass p = (Pass) u;
			this.unlock(p);
		}

		else
		{
			Message.sendGameMessage("This object can't be used to open this door.");
		}
	}
}