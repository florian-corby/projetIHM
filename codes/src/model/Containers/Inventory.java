package model.Containers;

import java.io.Serializable;
import java.util.*;
import model.Items.*;

public class Inventory implements Serializable {

	private final HashMap<String, Item> ITEMS;

	public Inventory()
	{
		this.ITEMS = new HashMap<>();
	}

	public void addItem(Item item)
	{
		this.ITEMS.put(item.getTag(), item);
	}

	public Item getItem(String s)
	{
			return this.ITEMS.get(s);
	}

	public int getSize()
	{
		return this.ITEMS.size();
	}

	public void moveItem(String tag, Inventory inventory)
	{
		Item item = this.ITEMS.get(tag);

		if(item != null) {
			this.removeItem(tag);
			inventory.addItem(item);
		}

		else
		{
			System.out.println("Error :> This item isn't in this inventory");
		}
	}

	public boolean isEmpty()
	{
		return this.ITEMS.isEmpty();
	}

	public void showItems()
	{
		for (Item i : this.ITEMS.values())
		{
			System.out.println("\t- " + i.getTag() + " : " + i.getDescription());
		}
	}

	public void removeItem(String tag)
	{
		this.ITEMS.remove(tag);
	}
}