package Location;

import java.io.Serializable;
import java.util.*;

import Commands.Lookable;
import Containers.Inventory;
import Doors.*;
import Characters.*;

public class Room implements Lookable, Serializable {

	private final Ship SHIP;
	private final Inventory INVENTORY;

	private final int ID;
	private final String description;

	private final HashMap<Door, Room> doors;
	private final HashMap<String, Actor> actors;

	public Room(Ship ship, int id, String description)
	{
		this.SHIP = ship;
		this.INVENTORY = new Inventory();
		this.ID = id;
		this.description = description;

		this.doors = new HashMap<>();
		this.actors = new HashMap<>();
	}

	public void addActor(Actor actor)
	{
		this.actors.put(actor.getName(), actor);
	}

	public void addDoor(Door d, Room r)
	{
		this.doors.put(d, r);
	}

	@Override
	public void describe()
	{
		System.out.println(this.description);

		if(this.doors.size() == 1 && this.hasLockedDoor())
			System.out.println("Suddenly, the door closed shut behind you! You try opening it... " +
					"But it is hopeless, you are trapped in this room.");

		this.scanRoom();
	}

	public Actor getActor(String s)
	{
		return this.actors.get(s);
	}

	public Door getDoor(String s)
	{
		Set<Door> doorSet = this.doors.keySet();
		Door res = null;

		for(Door d : doorSet)
		{
			if(d.getTag().equals(s))
				res = d;
		}

		return res;
	}

	public Door getDoor(Room r)
	{
		Door res = null;

		for(Map.Entry<Door, Room> e : this.doors.entrySet())
		{
			if(e.getValue().equals(r)) {
				res = e.getKey();
				break;
			}
		}

		return res;
	}

	public int getID() {
		return ID;
	}

	public Inventory getInventory()
	{
		return this.INVENTORY;
	}

	public LockedDoor getLockedDoor(String s)
	{
		return (LockedDoor) getDoor(s);
	}

	public boolean hasActor(String name)
	{
		return this.actors.get(name) != null;
	}

	public boolean hasLockedDoor()
	{
		Set<Door> doorSet = this.doors.keySet();
		boolean res = false;

		for(Door d : doorSet)
		{
			if (d instanceof LockedDoor && ((LockedDoor) d).isLocked()) {
				res = true;
				break;
			}
		}

		return res;
	}

	public void removeActor(String name)
	{
		this.actors.remove(name);
	}

	public void scanRoom()
	{
		//Printing items:
		System.out.println("\n\tObjects in the room:");
		this.getInventory().showItems();

		//Printing doors:
		System.out.println("\n\tDoors in the room:");
		Set<Door> doorSet = this.doors.keySet();

		for(Door d : doorSet)
			System.out.println("\t- " + d.getTag());

		//Printing actors:
		System.out.println("\n\tBeings in the room:");
		for(Actor a : this.actors.values())
		{
			if(a.getName().equals("me"))
				System.out.println("\t- You are in the Room");

			else if(!(a.isDead()))
				System.out.println("\t- " + a.getName() + " is in the Room");

			else
				System.out.println("\t- " + a.getName() + "'s body is in the Room");
		}
	}

	public void useDoor(Actor a, Door d)
	{
		if( d.isOpen())
			a.changeRoom(this.doors.get(d));

		else
			System.out.println("You can't use this door.");
	}
}