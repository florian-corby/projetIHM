package Doors;

import Commands.Lookable;
import Items.UsableBy;

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
			System.out.println(this.TAG + " is open.");

		else
			System.out.println(this.TAG + " is closed.");
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