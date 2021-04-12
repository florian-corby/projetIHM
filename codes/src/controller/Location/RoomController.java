package controller.Location;

import controller.Characters.Actor;
import controller.Commands.Lookable;
import controller.Containers.InventoryController;
import controller.Doors.DoorController;
import controller.Doors.LockedDoorController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomController implements Lookable, Serializable {

	private final ShipController SHIP;
	private final InventoryController INVENTORYController;

	private final int ID;
	private final String description;

	private final HashMap<DoorController, RoomController> doors;
	private final HashMap<String, Actor> actors;

	public RoomController(ShipController ship, int id, String description)
	{
		this.SHIP = ship;
		this.INVENTORYController = new InventoryController();
		this.ID = id;
		this.description = description;

		this.doors = new HashMap<>();
		this.actors = new HashMap<>();
	}

	public void addActor(Actor actor)
	{
		this.actors.put(actor.getName(), actor);
	}

	public void addDoor(DoorController d, RoomController r)
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

	public DoorController getDoor(String s)
	{
		Set<DoorController> doorControllerSet = this.doors.keySet();
		DoorController res = null;

		for(DoorController d : doorControllerSet)
		{
			if(d.getTag().equals(s))
				res = d;
		}

		return res;
	}

	public DoorController getDoor(RoomController r)
	{
		DoorController res = null;

		for(Map.Entry<DoorController, RoomController> e : this.doors.entrySet())
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

	public InventoryController getInventory()
	{
		return this.INVENTORYController;
	}

	public LockedDoorController getLockedDoor(String s)
	{
		return (LockedDoorController) getDoor(s);
	}

	public boolean hasActor(String name)
	{
		return this.actors.get(name) != null;
	}

	public boolean hasLockedDoor()
	{
		Set<DoorController> doorControllerSet = this.doors.keySet();
		boolean res = false;

		for(DoorController d : doorControllerSet)
		{
			if (d instanceof LockedDoorController && ((LockedDoorController) d).isLocked()) {
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
		Set<DoorController> doorControllerSet = this.doors.keySet();

		for(DoorController d : doorControllerSet)
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

	public void useDoor(Actor a, DoorController d)
	{
		if( d.isOpen())
			a.changeRoom(this.doors.get(d));

		else
			System.out.println("You can't use this door.");
	}
}