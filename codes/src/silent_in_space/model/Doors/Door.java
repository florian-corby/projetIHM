package silent_in_space.model.Doors;

import silent_in_space.model.Commands.Lookable;
import silent_in_space.model.Game.Message;
import silent_in_space.model.Items.UsableBy;
import silent_in_space.model.Utils.Scalar2D;

import java.io.Serializable;

public class Door implements Serializable, Lookable, UsableBy {

	private boolean isOpen;
	private final String TAG;
	private Scalar2D scalar2D;

	public Door(String tag) {
		this.isOpen = false;
		this.TAG = tag;
	}

	public Door(String tag, Scalar2D scalar2D) {
		this(tag);
		this.scalar2D = scalar2D;
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

	public Scalar2D getScalar2D() { return scalar2D; }
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