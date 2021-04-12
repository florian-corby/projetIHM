package controller.Characters;

import controller.Commands.Lookable;
import controller.Items.Item;
import controller.Location.RoomController;

import java.io.Serializable;
import java.util.List;

public class NPCController extends Actor implements Serializable, Lookable
{
	private boolean isHostile;
	private boolean isAlly;
	private String speech;

	public NPCController(String name, String description, boolean isHostile, boolean isAlly, List<Item> items, RoomController r)
	{
		super(name, description, r);
		this.isHostile = isHostile;
		this.isAlly = isAlly;

		for(Item i : items)
		{
			this.getInventory().addItem(i);
		}
	}

	public boolean isAlly()
	{
		return this.isAlly;
	}

	@Override
	public void isAttacked(Attacker a)
	{
		super.isAttacked(a);

		if(this.isDead())
			System.out.println(this.getName() + " is dead...");

		else
		{
			System.out.println(this.getName() + " gasps with pain, " + this.getName() + " only has " + this.getHp() + "hp left!");

			if (this.isAlly)
			{
				this.isAlly = false;
			}

			else
			{
				if (!(this.isHostile))
					this.isHostile = true;

				if(a instanceof Attackable) {
					this.attack((Attackable) a);
				}
			}
		}
	}

	public boolean isHostile()
	{
		return this.isHostile;
	}

	@Override
	public void receive(Actor a, String tag) {
		System.out.println(this.getName() + " wonders why you gave him this item, but takes it anyway.");
	}

	public void setAlly(boolean b) { this.isAlly = b; }
	public void setHostile(boolean b) {this.isHostile = b; }
	public void setSpeech(String s)
	{
		this.speech = s;
	}

	public void talk()
	{
		if (speech != null && !(this.isDead()) && !(this.isHostile))
			System.out.println(this.getName() + " - " + this.speech + "\n");
		else
			System.out.println("This person has nothing to say to you..."+ "\n");
	}
}