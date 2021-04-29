package silent_in_space.model.Characters;

import silent_in_space.model.Commands.Lookable;
import silent_in_space.model.Game.Message;
import silent_in_space.model.Items.*;
import silent_in_space.model.Location.*;
import silent_in_space.model.Containers.*;
import silent_in_space.model.Utils.Scalar2D;

import java.io.Serializable;

public abstract class Actor implements Attackable, Attacker, UsableBy, Serializable, Lookable
{
	private final String NAME;
	private final String DESCRIPTION;
	private int hp;
	private final int ATTACKPOWER;
	private Room room;
	private Room previousRoom;
	private final Inventory INVENTORY;
	private Scalar2D pos;

	private static final int DEFAULT_ATTACKPOWER = 25;
	private static final int DEFAULT_HP = 100;
	private static final int DEFAULT_HP_MAX = 100;

	public Actor(String name, String description, Room r)
	{
		this.NAME = name;
		this.DESCRIPTION = description;
		this.hp = DEFAULT_HP;
		this.ATTACKPOWER = DEFAULT_ATTACKPOWER;
		this.room = r;
		this.previousRoom = r;
		this.INVENTORY = new Inventory();

		r.addActor(this);
	}

	public Actor(String name, String description, Room r, Scalar2D pos)
	{
		this.NAME = name;
		this.DESCRIPTION = description;
		this.hp = DEFAULT_HP;
		this.ATTACKPOWER = DEFAULT_ATTACKPOWER;
		this.room = r;
		this.previousRoom = r;
		this.INVENTORY = new Inventory();
		this.pos = pos;

		r.addActor(this);
	}

	public void changeRoom(Room r)
	{
		this.previousRoom = this.getRoom();
		this.room.removeActor(this.NAME);
		r.addActor(this);
		this.room = r;
		this.room.describe();
	}

	@Override
	public void describe()
	{
		Message.sendGameMessage(this.DESCRIPTION);
	}

	public void drop(Item item)
	{
		this.getInventory().removeItem(item.getTag());
		this.getRoom().getInventory().addItem(item);
		Message.sendGameMessage(item.getTag() + " was dropped on the floor. ");
	}

	public int getAttackPower()
	{
		return this.ATTACKPOWER;
	}

	public int getDEFAULT_HP_MAX()
	{
		return DEFAULT_HP_MAX;
	}

	public int getHp()
	{
		return this.hp;
	}

	public Inventory getInventory()
	{
		return this.INVENTORY;
	}

	public String getName()
	{
		return this.NAME;
	}

	public Scalar2D getPos() { return this.pos; }

	public Room getPreviousRoom()
	{
		return this.previousRoom;
	}

	public Room getRoom()
	{
		return this.room;
	}

	public void setPos(int x, int y) {this.pos = new Scalar2D(x, y);}

	public void give(String tag, Actor a)
	{
		Item item = this.INVENTORY.getItem(tag);

		if(!this.isDead())
		{
			if(a.isDead())
				Message.sendGameMessage("You tried giving a " + tag + " to " + a.getName() + ", but somehow it seems that a dead body cannot grab an item. Strange, huh?");
			else {
				if(item != null) {
					if((a instanceof NPC && !((NPC) a).isHostile()) || a instanceof Player)
						this.INVENTORY.moveItem(item.getTag(), a.getInventory());

					a.receive(this, item.getTag());
				}

				else
				{
					if(this instanceof Player)
						Message.sendGameMessage("Error :> You don't have this item in your inventory");

					else
						Message.sendGameMessage("Error :> This item isn't in giver's inventory");
				}
			}
		}
	}

	@Override
	public void isAttacked(Attacker a)
	{
		if(a instanceof Actor)
		{
			Actor actor = (Actor) a;

			if(!(this.isDead()))
				this.hp -= actor.getAttackPower();
		}
	}

	public boolean isDead()
	{
		return this.hp <= 0;
	}

	public void isHealed(int healing_points)
	{
		if(this.isDead())
			Message.sendGameMessage("A dead person can't be healed... You really have a few things to learn about life, don't you?");

		else
		{
			this.hp += healing_points;

			if(this.hp > this.getDEFAULT_HP_MAX())
				this.hp = this.getDEFAULT_HP_MAX();

			if(this instanceof Player)
				Message.sendGameMessage("You have been healed!");

			else
				Message.sendGameMessage(this.getName() + " has been healed!");
		}
	}

	@Override
	public void isUsedBy(UsableOn u)
	{
		if(u instanceof HealthStation)
			this.isHealed(DEFAULT_HP_MAX - this.hp);

		else
			Message.sendGameMessage("Error :> This object has no effect here");
	}

	public abstract void receive(Actor a, String tag);

	public void setPos(Scalar2D pos) { this.pos = pos; }
}