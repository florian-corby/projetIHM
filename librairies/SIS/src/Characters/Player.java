package Characters;

import Commands.Command;
import Commands.Lookable;
import Commands.UnknownVerb;
import Doors.Door;
import Game.SIS;
import Items.*;
import Location.Room;
import Location.Ship;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Player extends Actor implements Serializable
{
	private final Ship SHIP;
	private SIS sis;
	private static final String NAME = "me";
	private static final String DESCRIPTION = "Narcissism is an ugly trait of character but it is so common among humans";

	public Player(Room r, Ship s)
	{
		super(NAME, DESCRIPTION, r);
		this.SHIP = s;
	}

	public void back()
	{
		try {
			Door d = this.getRoom().getDoor(this.getPreviousRoom());
			this.go(d);
		}

		catch(NullPointerException e)
		{
			System.out.println("You can't go back");
		}
	}

	public void call()
	{
		System.out.print("\nCommand :> ");
		Scanner sc = new Scanner(System.in);
		String buffer = sc.nextLine();
		System.out.println();
		String[] words = buffer.split(" ");

		String verb = words[0];
		List<String> args = new ArrayList<>();

		if(words.length > 1)
			args.addAll(Arrays.asList(words).subList(1, words.length));

		try {
			Command cmd = new Command(this, verb, args);
			cmd.exec();
		}

		catch(UnknownVerb e)
		{
			System.out.println("Enter help for valid verbs");
		}
	}

	public void drop(Item item)
	{
		this.getInventory().removeItem(item.getTag());
		this.getRoom().getInventory().addItem(item);
		System.out.println("You have dropped " + item.getTag());
	}

	@Override
	public void give(String tag, Actor a)
	{
		Item item = this.getInventory().getItem(tag);

		if(item != null && !item.isGivable())
			System.out.println("Error :> You can't give this item");

		else
			super.give(tag, a);
	}

	public void go(Door door)
	{
		door.open();
		this.getRoom().useDoor(this, door);
	}

	public void help()
	{
		System.out.println("You can interact with the game using textual commands. " +
				"\nHere's an exhaustive list of these commands, their syntaxes and of their effects\n" +
				"(optional arguments are into brackets): \n");

		System.out.println("\t- attack <assailable> : attack the designated thing you can hit");
		System.out.println("\t- back : quick return to the previous room");
		System.out.println("\t- drop <item> : drop the designated item on the floor");
		System.out.println("\t- give <object's name> <NPC name>: give of an item (if you are allowed to do it) of your inventory to an NPC.");
		System.out.println("\t- go <door name> : go to a neighbour room using the indicated door\n");

		System.out.println("\t- help : display this help menu");
		System.out.println("\t- info : display the stats of your character");
		System.out.println("\t- inventory : display the content of your inventory");
		System.out.println("\t- load : load a saved game");
		System.out.println("\t- look [<something or somebody>] : display the description of your surroundings or of the indicated parameter\n");

		System.out.println("\t- quit : leave the game");
		System.out.println("\t- save : save the current state of the game");
		System.out.println("\t- search <NPC name> : get access to the inventory of a dead NPC");
		System.out.println("\t- take <object's name> : take the indicated object");
		System.out.println("\t- talk <npc> : talk to the designated npc\n");

		System.out.println("\t- use <object's name> [<object's name>] : use an object possibly on another indicated object");
	}

	public void info()
	{
		System.out.println("You have " + this.getHp() + "hp");
		System.out.println("You have " + this.getAttackPower() + " attack power");
	}

	@Override
	public void isAttacked(Attacker a)
	{
		super.isAttacked(a);

		if(this.isDead())
			System.out.println("You are now dead...");

		else {
			if (a instanceof Actor) {
				Actor actor = (Actor) a;

				if(actor.getName().equals(this.getName()))
					System.out.println("You hit yourself! You've just lost " + actor.getAttackPower() + "hp! So much for your mental health...");

				else
					System.out.println(actor.getName() + " hit you! You've just lost " + actor.getAttackPower() + "hp!");
			}
		}
	}

	@Override
	public void isUsedBy(UsableOn u)
	{
		super.isUsedBy(u);

		if(u instanceof Computer)
			System.out.println("Isn't using a computer on yourself called technophilia?");

		else if(u instanceof Pass)
			System.out.println("Using a pass on yourself looks dumb... You really aren't helping the human cause here!");
	}

	public void load()
	{
		this.sis.load();
	}

	public void look()
	{
		this.getRoom().describe();
	}

	public void look(Lookable l)
	{
		l.describe();
	}

	public void quit()
	{
		System.out.println("Thanks for playing Silent In Space!");
		System.exit(0);
	}

	public void take(Item item)
	{
		if(!(item.isTakable())) {
			System.out.println("Error :> You can't take this item with you");
		}

		else
		{
			this.getRoom().getInventory().removeItem(item.getTag());
			this.getInventory().addItem(item);
			System.out.println("You have taken " + item.getTag());
		}
	}

	public void talk(NPC npc)
	{
		if(npc.isDead())
			System.out.println("Great, now you are talking to a dead body... You're just getting better and better!");

		else
			npc.talk();
	}


	public void use(Item item)
	{
		item.isUsed(this);
	}

	public void use(UsableOn on, UsableBy by)
	{
		by.isUsedBy(on);
	}

	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream("saveData.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			oos.writeObject(this.SHIP);
			oos.close();
			System.out.println("You successfully saved the game!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void search(NPC npc) {
		if(npc.isDead()) {
			Scanner scan = new Scanner(System.in);
			String userChoice = "";
			while(!userChoice.equals("quit")){
				System.out.println("\n==========================================================================================\n" +
						"\tYou are searching " + npc.getName() + "'s inventory.\n" +
						"\tEnter the name of an item in order to take it. Enter 'quit' to go back.\n" +
						"==========================================================================================\n");
				npc.getInventory().showItems();
				System.out.print(":> ");
				userChoice = scan.nextLine();
				if(!userChoice.equals("quit")){
					npc.give(userChoice, this);
				}
				else System.out.println("You decided to stop looting " + npc.getName() + "'s dead corpse.");
			}
		}
		else
			System.out.println(npc.getName() + " looks at you trying to search their pockets, and pushes you backward while " +
					"wondering if all humans are this rude.");
	}

	public void setSIS(SIS sis)
	{
		this.sis = sis;
	}

	@Override
	public void receive(Actor a, String tag) {
		System.out.println("You took the " + tag + ".");
	}
}