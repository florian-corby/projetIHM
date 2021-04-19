package model.Items;

import model.Utils.Scalar2D;

import java.io.Serializable;

public class HealthStation extends Item implements Serializable
{
	public HealthStation(String tag, String description) { super(tag, description, new Scalar2D(0, 0)); }
	public HealthStation(String tag, String description, Scalar2D scalar2D) { super(tag, description, scalar2D); }

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