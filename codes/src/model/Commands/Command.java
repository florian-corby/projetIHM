package model.Commands;

import model.Characters.*;
import model.Doors.Door;
import model.Items.Item;
import model.Items.UsableBy;
import model.Items.UsableOn;

import java.util.List;

public class Command {

	private final Player CALLER;
	private final Converter CONVERTER;
	private final Verb V;
	private final List<String> ARGS;

	public Command(Player player, String verb, List<String> args) throws UnknownVerb
	{
		this.CALLER = player;
		this.ARGS = args;

		Parser parser = new Parser(verb);
		this.V = parser.getVerb();
		this.CONVERTER = new Converter(this.CALLER);
	}

	public void exec()
	{
		switch(this.V)
		{
			case ATTACK:
				if(this.ARGS.size() == 0)
					System.out.println("Error :> Please indicate who you want to attack");

				else {
					try {
						Attackable a = this.CONVERTER.convertAttackable(this.ARGS.get(0));
						this.CALLER.attack(a);
					} catch (StringRequestUnmatched e) {
						System.out.println("Error :> You can't attack this!");
					}
				}
				break;

			case BACK:
				this.CALLER.back();
				break;

			case DROP:
				if(this.ARGS.size() == 0)
					System.out.println("Error :> Please indicate which item you want to drop");

				else {
					try {
						Item item = this.CONVERTER.convertPlayerItem(this.ARGS.get(0));
						this.CALLER.drop(item);
					} catch (StringRequestUnmatched e) {
						System.out.println("Error :> This item isn't in your inventory");
					}
				}
				break;

			case GIVE:
				if(this.ARGS.size() < 1)
				{
					System.out.println("Error :> I don't know what to give to whom.");
				}

				else if(ARGS.size() == 1)
				{
					System.out.println("Error :> I don't know who to give " + this.ARGS.get(0));
				}

				else
				{
					try {
						Actor a = this.CONVERTER.convertNPC(this.ARGS.get(1));
						this.CALLER.give(this.ARGS.get(0), a);
					} catch (StringRequestUnmatched e) {
						System.out.println("You can't give anything to this!");
					}
				}
				break;

			case GO:
				if(this.ARGS.size() == 0)
					System.out.println("Error :> Please indicate which door you want to go through");

				else {
					try {
						Door d = this.CONVERTER.convertDoor(this.ARGS.get(0));
						this.CALLER.go(d);
					} catch (StringRequestUnmatched e) {
						System.out.println("Error :> This isn't a door!");
					}
				}
				break;

			case HELP:
				this.CALLER.help();
				break;

			case INFO:
				this.CALLER.info();
				break;

			case INVENTORY:
				this.CALLER.getInventory().showItems();
				break;

			case LOAD:
				this.CALLER.load();
				break;

			case LOOK:
				if(ARGS.size() == 0)
					this.CALLER.look();

				else
				{
					try {
						Lookable l = this.CONVERTER.convertLookable(this.ARGS.get(0));
						this.CALLER.look(l);
					}

					catch(StringRequestUnmatched e) {
						System.out.println("Error :> You can't look this.");
					}
				}
				break;

			case QUIT:
				CALLER.quit();
				break;

			case SAVE:
				this.CALLER.save();
				break;

			case SEARCH:
				if(ARGS.size() == 0)
					System.out.println("Error :> Who are you trying to search?");

				else {
					try {
						NPC npc = this.CONVERTER.convertNPC(this.ARGS.get(0));
						this.CALLER.search(npc);
					} catch (StringRequestUnmatched e) {
						System.out.println("Error :> This NPC doesn't exist.");
					}
				}
				break;

			case TAKE:
				if(ARGS.size() == 0)
					System.out.println("Error :> Please indicate which item you want to take");

				else {
					try {
						Item item = this.CONVERTER.convertItem(this.ARGS.get(0));
						this.CALLER.take(item);
					} catch (StringRequestUnmatched e) {
						System.out.println("Error :> This item isn't in this room or can't be taken with you");
					}
				}
				break;

			case TALK:
				if(this.ARGS.size() == 0)
					System.out.println("Error :> Please indicate who you want to talk to");

				else {

					try {
						if (this.ARGS.get(0).equals("me"))
							System.out.println("I have to bring a proof of what's happening here to Umhon before leaving...");
						else {
							NPC npc = this.CONVERTER.convertNPC(this.ARGS.get(0));
							this.CALLER.talk(npc);
						}
					} catch (StringRequestUnmatched e) {
						System.out.println("Error :> This person isn't in this room (or maybe you enjoy talking to ghosts?)");
					}
				}
				break;

			case USE:
				if(this.ARGS.size() < 1)
				{
					System.out.println("Error :> I don't know which item you want to use.");
				}

				else if(ARGS.size() == 1)
				{
					try {
						Item item = this.CONVERTER.convertItem(this.ARGS.get(0));
						this.CALLER.use(item);
					}

					catch(StringRequestUnmatched e)
					{
						System.out.println("Error :> I don't know this item");
					}
				}

				else
				{
					try
					{
						UsableOn on = this.CONVERTER.convertUsableOn(this.ARGS.get(0));
						UsableBy by = this.CONVERTER.convertUsableBy(this.ARGS.get(1));
						this.CALLER.use(on, by);
					}

					catch(StringRequestUnmatched e)
					{
						System.out.println("Error :> I don't know one of the items");
					}
				}
				break;

			default:
				System.out.println("Error :> Something wrong occurred, this case should never have been reached in exec()" +
						" method in commands.");
				break;
		}
	}
}