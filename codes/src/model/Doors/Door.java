package model.Doors;

import model.Commands.Lookable;
import model.Game.Message;
import model.Items.UsableBy;

import java.io.Serializable;

public class Door implements Serializable, Lookable, UsableBy {

	private boolean isOpen;
	private final String TAG;

	public Door(String tag)
	{
		this.isOpen = false;
		this.TAG = tag;
	}

	public void close()
	{
		this.isOpen = false;
	}

	@Override
	public void describe()
	{
		if(this.isOpen)
			Message.sendGameMessage(this.TAG + " is open.");

		else
			Message.sendGameMessage(this.TAG + " is closed.");
	}

	public String getTag()
	{
		return this.TAG;
	}

	public boolean isOpen()
	{
		return this.isOpen;
	}

	public void open()
	{
		this.isOpen = true;
	}
}