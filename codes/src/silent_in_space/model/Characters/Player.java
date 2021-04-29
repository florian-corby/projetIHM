package silent_in_space.model.Characters;

import silent_in_space.model.Commands.Command;
import silent_in_space.model.Commands.Lookable;
import silent_in_space.model.Commands.UnknownVerb;
import silent_in_space.model.Doors.Door;
import silent_in_space.model.Game.Message;
import silent_in_space.model.Game.SIS;
import silent_in_space.model.Items.*;
import silent_in_space.model.Location.Room;
import silent_in_space.model.Location.Ship;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Player extends Actor implements Serializable
{
	private final Ship SHIP;
	private SIS sis;
	private static final String NAME = "Me";
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
			Message.sendGameMessage("You can't go back");
		}
	}

	public void call()
	{
		Message.sendGameMessage("\nCommand :> ");
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
			Message.sendGameMessage("Enter help for valid verbs");
		}
	}

	@Override
	public void give(String tag, Actor a)
	{
		Item item = this.getInventory().getItem(tag);

		if(item != null && !item.isGivable())
			Message.sendGameMessage("Error :> You can't give this item");

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
		Message.sendGameMessage("You can interact with the game using textual commands. " +
				"\nHere's an exhaustive list of these commands, their syntaxes and of their effects\n" +
				"(optional arguments are into brackets): \n");

		Message.sendGameMessage("\t- attack <assailable> : attack the designated thing you can hit");
		Message.sendGameMessage("\t- back : quick return to the previous room");
		Message.sendGameMessage("\t- drop <item> : drop the designated item on the floor");
		Message.sendGameMessage("\t- give <object's name> <NPC name>: give of an item (if you are allowed to do it) of your inventory to an NPC.");
		Message.sendGameMessage("\t- go <door name> : go to a neighbour room using the indicated door\n");

		Message.sendGameMessage("\t- help : display this help menu");
		Message.sendGameMessage("\t- info : display the stats of your character");
		Message.sendGameMessage("\t- inventory : display the content of your inventory");
		Message.sendGameMessage("\t- load : load a saved game");
		Message.sendGameMessage("\t- look [<something or somebody>] : display the description of your surroundings or of the indicated parameter\n");

		Message.sendGameMessage("\t- quit : leave the game");
		Message.sendGameMessage("\t- save : save the current state of the game");
		Message.sendGameMessage("\t- search <NPC name> : get access to the inventory of a dead NPC");
		Message.sendGameMessage("\t- take <object's name> : take the indicated object");
		Message.sendGameMessage("\t- talk <npc> : talk to the designated npc\n");

		Message.sendGameMessage("\t- use <object's name> [<object's name>] : use an object possibly on another indicated object");
	}

	public void info()
	{
		Message.sendGameMessage("You have " + this.getHp() + "hp\n");
		Message.sendGameMessage("You have " + this.getAttackPower() + " attack power");
	}

	@Override
	public void isAttacked(Attacker a)
	{
		super.isAttacked(a);

		if(this.isDead())
			Message.sendGameMessage("You are now dead...");

		else {
			if (a instanceof Actor) {
				Actor actor = (Actor) a;

				if(actor.getName().equals(this.getName()))
					Message.sendGameMessage("You hit yourself! You've just lost " + actor.getAttackPower() + "hp! So much for your mental health...");

				else
					Message.sendGameMessage(actor.getName() + " hit you! You've just lost " + actor.getAttackPower() + "hp!");
			}
		}
	}

	@Override
	public void isUsedBy(UsableOn u)
	{
		super.isUsedBy(u);

		if(u instanceof Computer)
			Message.sendGameMessage("Isn't using a computer on yourself called technophilia?");

		else if(u instanceof Pass)
			Message.sendGameMessage("Using a pass on yourself looks dumb... You really aren't helping the human cause here!");
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
		Message.sendGameMessage("Thanks for playing Silent In Space!");
		System.exit(0);
	}

	public void take(Item item)
	{
		if(!(item.isTakable())) {
			Message.sendGameMessage("Error :> You can't take this item with you");
		}

		else
		{
			this.getRoom().getInventory().removeItem(item.getTag());
			this.getInventory().addItem(item);
			Message.sendGameMessage("You have taken " + item.getTag());
		}
	}

	public void talk(NPC npc)
	{
		if(npc.isDead())
			Message.sendGameMessage("Great, now you are talking to a dead body... You're just getting better and better!");

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
			Message.sendGameMessage("You successfully saved the game!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void search(NPC npc) {
		if(npc.isDead()) {
			Scanner scan = new Scanner(System.in);
			String userChoice = "";
			while(!userChoice.equals("quit")){
				Message.sendGameMessage("\n==========================================================================================\n" +
						"\tYou are searching " + npc.getName() + "'s inventory.\n" +
						"\tEnter the name of an item in order to take it. Enter 'quit' to go back.\n" +
						"==========================================================================================\n");
				npc.getInventory().showItems();
				System.out.print(":> ");
				userChoice = scan.nextLine();
				if(!userChoice.equals("quit")){
					npc.give(userChoice, this);
				}
				else Message.sendGameMessage("You decided to stop looting " + npc.getName() + "'s dead corpse.");
			}
		}
		else
			Message.sendGameMessage(npc.getName() + " looks at you trying to search their pockets, and pushes you backward while " +
					"wondering if all humans are this rude.");
	}

	public void setSIS(SIS sis)
	{
		this.sis = sis;
	}

	@Override
	public void receive(Actor a, String tag) {
		Message.sendGameMessage("You took the " + tag + ".");
	}
}