package model.Doors;

import model.Commands.Lookable;
import model.Game.Message;
import model.Items.UsableBy;
import model.Utils.Pos2D;

import java.io.Serializable;

public class Door implements Serializable, Lookable, UsableBy {

	private boolean isOpen;
	private final String TAG;
	private Pos2D pos2D;

	public Door(String tag) {
		this.isOpen = false;
		this.TAG = tag;
	}

	public Door(String tag, Pos2D pos2D) {
		this(tag);
		this.pos2D = pos2D;
	}

	public void close()
	{
		this.isOpen = false;
	}

	@Override
	public void describe() {
		if(this.isOpen)
			Message.sendGameMessage(this.TAG + " is open.");
		else
			Message.sendGameMessage(this.TAG + " is closed.");
	}

	public Pos2D getPos2D() { return pos2D; }
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