package model.Items;

import model.Utils.Pos2D;

import java.io.Serializable;

public class HealthStation extends Item implements Serializable
{
	public HealthStation(String tag, String description) { super(tag, description, new Pos2D(0, 0)); }
	public HealthStation(String tag, String description, Pos2D pos2D) { super(tag, description, pos2D); }

	@Override
	public void describe()
	{
		System.out.println(this.getDescription());
	}

	@Override
	public void isUsed(UsableBy u)
	{
		System.out.println("I need you to tell me who needs to be healed. (For example: use HealthStation me if you want to use it on yourself)");
	}
}