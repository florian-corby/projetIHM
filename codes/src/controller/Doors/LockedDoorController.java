package controller.Doors;

import controller.Items.Pass;
import controller.Items.PassType;
import controller.Items.UsableOn;

import java.io.Serializable;

public class LockedDoorController extends DoorController implements Unlockable, Serializable {

	private final PassType PASSTYPE;
	private boolean isLocked;

	public LockedDoorController(String tag, PassType p)
	{
		super(tag);
		this.PASSTYPE = p;
		this.isLocked = true;
	}

	public void describe()
	{
		super.describe();
		if(this.isLocked)
			System.out.println("This door is locked! Some kind of letter " + this.PASSTYPE.toString() + " is written on it...");

		else
			System.out.println("This door is unlocked!");
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void open()
	{
		if(!isLocked)
			super.open();

		else
			System.out.println("This door is locked! A letter " + this.PASSTYPE.toString() + " is written on it...");
	}

	@Override
	public void unlock(Pass p)
	{
		if(this.PASSTYPE == p.getPassType())
		{
			this.isLocked = false;
			System.out.println("You have unlocked the " + this.getTag() + " !");
		}

		else
		{
			System.out.println("You can't unlock the " + this.getTag() + " with this pass.");
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
			System.out.println("This object can't be used to open this door.");
		}
	}
}