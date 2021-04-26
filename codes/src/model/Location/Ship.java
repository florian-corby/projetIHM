package model.Location;

import model.Characters.NPC;
import model.Characters.Player;
import model.Characters.Umhon;
import model.Doors.Door;
import model.Doors.LockedDoor;
import model.Events.Event;
import model.Events.PlayerEvent;
import model.Items.*;
import model.Utils.Scalar2D;

import java.io.Serializable;
import java.util.*;

public class Ship implements Serializable {

	private final HashMap<Integer, Room> ROOMS;
	private final Player PLAYER;
	private HashMap<String, NPC> npcs = new HashMap<>();

	public Ship()
	{
		ROOMS = new HashMap<>();

		// =================================================================================================== //
		// ================================= Création des pièces du vaisseau ================================= //
		// =================================================================================================== //
		Room room11 = new Room(this, 11, "Room 11: This is the bedroom of a baby alien! Strangely... He (or she? Or it?) looks cute!");

		Room room13 = new Room(this, 13, "Room 13: This looks like a corridor between two parts of the ship. " +
				"You can see the vast emptiness of space through the windows.");

		Room room14 = new Room(this, 14, "Room 14: This room looks tiny for a family. Yet there's everything they need: " +
				"bunk beds, a kitchen, even a sofa.");

		Room room17 = new Room(this, 17, "Room 17: A low flickering out is emitted by a strange bulb. " +
				"There doesn't seem to be anything interesting here.");

		Room room18 = new Room(this, 18, "Room 18: There's nobody here and yet you feel like " +
				"there's a lot of activity near this room.");

		Room room19 = new Room(this, 19, "Room 19: You are in what looks like the entrance of a block of flats");

		Room room21 = new Room(this, 21, "Room 21: This room is filled with unknown objects. " +
				"There's a table in the middle on which you were laying.");

		Room room22 = new Room(this, 22, "Room 22: Eerie lights are flowing out of the walls " +
				"pulsating slowly, quietly.");

		Room room23 = new Room(this, 23, "Room 23: In the obscurity, you see the light of a computer screen left on the table.");

		Room room24 = new Room(this, 24, "Room 24: This room is big and cozy. Even though you don't exactly understand " +
				"the use of some pieces of furniture they look sophisticated and expansive!");

		Room room27 = new Room(this, 27, "Room 27: Boxes and vials are jamming this room. It looks as if you were getting" +
				" closer to a warehouse...");

		Room room28 = new Room(this, 28, "Room 28: There's nothing of interest here.");

		Room room29 = new Room(this, 29, "Room 29: You are in what looks like the entrance of a block of flats");

		Room room30 = new Room(this, 30, "Room 30: You are in warehouse. You can see what looks like food, drinks, tools and various" +
				" materials whether they are meant for experiments or the everyday life.");


		// =================================================================================================== //
		// ================================= Connexion des pièces du vaisseau ================================ //
		// =================================================================================================== //

		//Connexion de la pièce 11:
		Door door11To14 = new Door("door14", new Scalar2D(5, 10));
		room11.addDoor(door11To14, room14);

		//Connexion de la pièce 13:
		Door door13To17 = new Door("door17", new Scalar2D(5, 10));
		room13.addDoor(door13To17, room17);

		//Connexion de la pièce 14:
		Door door14To11 = new Door("door11", new Scalar2D(5, 0));
		room14.addDoor(door14To11, room11);
		Door door14To19 = new Door("door19", new Scalar2D(5, 10));
		room14.addDoor(door14To19, room19);

		//Connexion de la pièce 17:
		Door door17To22 = new Door("door22", new Scalar2D(5, 10));
		room17.addDoor(door17To22, room22);
		Door door17To13 = new Door("door13", new Scalar2D(5, 0));
		room17.addDoor(door17To13, room13);
		Door door17To18 = new Door("door18", new Scalar2D(10, 5));
		room17.addDoor(door17To18, room18);

		//Connexion de la pièce 18:
		Door door18To17 = new Door("door17", new Scalar2D(0, 5));
		room18.addDoor(door18To17, room17);
		LockedDoor door18To19 = new LockedDoor("doorA", PassType.A, new Scalar2D(10, 5));
		room18.addDoor(door18To19, room19);

		//Connexion de la pièce 19:
		Door door19To14 = new Door("door14", new Scalar2D(5, 0));
		room19.addDoor(door19To14, room14);
		Door door19To18 = new Door("doorA", new Scalar2D(0, 5));
		room19.addDoor(door19To18, room18);
		Door door19To24 = new Door("door24", new Scalar2D(5, 10));
		room19.addDoor(door19To24, room24);

		//Connexion de la pièce 21:
		LockedDoor door21To22 = new LockedDoor("doorT", PassType.T,  new Scalar2D(10, 5));
		room21.addDoor(door21To22, room22);

		//Connexion de la pièce 22:
		Door door22To21 = new Door("doorT", new Scalar2D(0, 5));
		room22.addDoor(door22To21, room21);
		Door door22To23 = new Door("door23", new Scalar2D(10, 5));
		room22.addDoor(door22To23, room23);
		Door door22To17 = new Door("door17", new Scalar2D(5, 0));
		room22.addDoor(door22To17, room17);
		Door door22To27 = new Door("door27", new Scalar2D(5, 10));
		room22.addDoor(door22To27, room27);

		//Connexion de la pièce 23:
		LockedDoor door23To22 = new LockedDoor("door22", PassType.C, new Scalar2D(0, 5));
		room23.addDoor(door23To22, room22);

		//Connexion de la pièce 24:
		Door door24To19 = new Door("door19", new Scalar2D(5, 0));
		room24.addDoor(door24To19, room19);
		Door door24To29 = new Door("door29", new Scalar2D(5, 10));
		room24.addDoor(door24To29, room29);

		//Connexion de la pièce 27:
		Door door27To22 = new Door("door22", new Scalar2D(5, 0));
		room27.addDoor(door27To22, room22);
		Door door27To28 = new Door("door28", new Scalar2D(10, 5));
		room27.addDoor(door27To28, room28);
		Door door27To30 = new Door("door30", new Scalar2D(5, 10));
		room27.addDoor(door27To30, room30);

		//Connexion de la pièce 28:
		Door door28To27 = new Door("door27", new Scalar2D(0, 5));
		room28.addDoor(door28To27, room27);

		//Connexion de la pièce 29:
		Door door29To24 = new Door("door24", new Scalar2D(5, 0));
		room29.addDoor(door29To24, room24);

		//Connexion de la pièce 30:
		Door door30To27 = new Door("door27", new Scalar2D(5, 0));
		room30.addDoor(door30To27, room27);
		Pass passA = new Pass("passA", "This is a pass... A letter 'A' is written on it.",
				PassType.A, new Scalar2D(1, 10));
		room30.getInventory().addItem(passA);

		// =================================================================================================== //
		// =================================== Création et Ajout des acteurs ================================= //
		// =================================================================================================== //

		// ~~~~~~~~~~~~~~~~ Ajouts d'acteurs dans la pièce 11:
		//Unien:
		List<Item> unienItems = new ArrayList<>();
		NPC unien = new NPC("Unien", "You know nothing about aliens... But this looks like a baby alien!",false, true, unienItems, room11);
		unien.setSpeech("Blrberbllirbrborllrzzal!");
		this.npcs.put(unien.getName(), unien);

		// ~~~~~~~~~~~~~~~~ Ajouts d'acteurs dans la pièce 14:
		//Ghainkix:
		List<Item> ghainkixItems = new ArrayList<>();
		NPC ghainkix = new NPC("Ghainkix", "I don't know anything about aliens' genders but Ghainkix definitely looks like a grown-up",
				false, true, ghainkixItems, room14);
		ghainkix.setSpeech("Hi! I'm Ghainkix, are you a human? They say that your species is in danger and that we should help you!");
		this.npcs.put(ghainkix.getName(), ghainkix);

		//Eeloir:
		List<Item> eeloirItems = new ArrayList<>();
		NPC eeloir = new NPC("Eeloir", "Eeloir looks calm and naturally kind... Could Eeloir be considered pretty among aliens?" +
				" Strangely you think Eeloir is pretty!", false, true, eeloirItems, room14);
		eeloir.setSpeech("Hi! I'm Eeloir, you must be a human, right? I am so glad to meet you!");
		this.npcs.put(eeloir.getName(), eeloir);

		//Braenzuds:
		List<Item> braenzudsItems = new ArrayList<>();
		NPC braenzuds = new NPC("Braenzuds", "Do aliens have to undergo a teenage phase in their lives to???",
				false, true, braenzudsItems, room14);
		braenzuds.setSpeech("Leave me alone... Can't you see I'm playing?");
		this.npcs.put(braenzuds.getName(), braenzuds);

		// ~~~~~~~~~~~~~~~~ Ajouts d'acteurs dans la pièce 21:
		//Kilen:
		List<Item> kilenItems = new ArrayList<>();
		Pass p = new Pass("passT", "It looks like a pass... There's some kind of letter looking like a T written on it.", PassType.T);
		kilenItems.add(p);
		NPC kilen = new NPC("Kilen", "With his white clothes, Kilen looks like a scientist. As of the " +
				"rest, it's beyond\nwhat any human has ever imagined about what aliens look like.", false, true, kilenItems, room21);
		kilen.setSpeech("Hi human! I'm Kilen. You are in danger, here's a pass to escape. Good luck!\nAnd please, " +
				"I'm begging you... Don't kill my friends!");
		this.npcs.put(kilen.getName(), kilen);

		//Le joueur:
		this.PLAYER = new Player(room21, this);

		// ~~~~~~~~~~~~~~~~ Ajouts d'acteurs dans la pièce 24:
		//Umhon:
		List<Item> umhonItems = new ArrayList<>();
		Umhon umhon = new Umhon("Umhon",  "Before meeting Umhon you couldn't think that aliens would feel so close to humans... " +
				"She clearly likes anything shiny. Her whole body is covered with jewels of all kinds",false, false, umhonItems, room24);
		umhon.setSpeech("""
				Oh a human! You poor thing, you must be lost... You know, my husband, Vik, is the captain's favorite. He even guards his personal computer! But I wonder, what they are doing to you poor things?
				...
				You know what? Bring me some evidence of what they are doing to your species, and I'll give you the code to the Captain's laptop!""");
		File CaptainCode = new File("CaptainCode", "The code to the the Captain's laptop", new Scalar2D(3, 6), true, false,"The code is: iwanttoeradicateallhumans");
		umhon.getInventory().addItem(CaptainCode);
		this.npcs.put(umhon.getName(), umhon);

		// ~~~~~~~~~~~~~~~~ Ajouts d'acteurs dans la pièce 29:
		//Eek'eads:
		List<Item> eekeadsItems = new ArrayList<>();
		NPC eekeads = new NPC("Eekeads", "You know nothing about aliens... But this looks like a baby alien!",
				false, true, eekeadsItems, room29);
		eekeads.setSpeech("GiagiaGia!");
		this.npcs.put(eekeads.getName(), eekeads);

		// =================================================================================================== //
		// =================================== Création et Ajout des objets ================================== //
		// =================================================================================================== //

		// ~~~~~~~~~~~~~~~~ Ajouts d'objets dans la pièce 14:

		File journal = new File("journal", "a journale belonging to the family from room 14.",
				"""
						All the files on this DataPad have the name dairy on it.
						It looks like Eelhoir's journal. She talks about her family, and how they escaped from the destruction of their planet.
						The last few entries mention their grim journey to Earth, with all the sacrifices and losses they made along the way.""");
		room14.getInventory().addItem(journal);

		// ~~~~~~~~~~~~~~~~ Ajouts d'objets dans la pièce 18:

		File clueToPassA = new File("wanted_poster", "It's a notice. Somebody posted it in a room hoping " +
				"that somebody would bring back his pass to the residential quarters", "Hi! It's Ghainkix. I lost my pass" +
				" yesterday after a hard day of work. I think it might be in the warehouse but I'm not sure. If somebody ever happen " +
				"to go there... Could you please bring it back to me? My wife's already complaining she has to open the door for me everytime" +
				" I get in or out... Thanks so much!");
		room18.getInventory().addItem(clueToPassA);

		// ~~~~~~~~~~~~~~~~ Ajouts d'objets dans la pièce 19:

		Sign sign = new Sign("sign", "An holographic sign.",
				"""
						The sign contains a map of this part of the ship. This room seems to serve as some kind of forum for the alien population. There are some habitations beyond both exits of this room.
						Someone seems to have somehow violated the holographic sign, by writing "I lov u Mary-Jane" everywhere. You can't read anything about the other rooms.
						A big red circle points to your current room, saying "YOU ARE HERE". This seems unnecessary.
						""", new Scalar2D(10, 6));

		room19.getInventory().addItem(sign);

		// ~~~~~~~~~~~~~~~~ Ajouts d'objets dans la pièce 21:

		HealthStation hs = new HealthStation("HealthStation", "This is a healthstation. I can heal myself " +
				"here as much as I want but I can't bring this with me.", new Scalar2D(9, 9));
		room21.getInventory().addItem(hs);
		Artefact statue = new Artefact("statue", "This is a statue showing an alien like Kilen... " +
				"Maybe to prove that this wasn't all a dream I should take it with me.", new Scalar2D(1, 2));
		room21.getInventory().addItem(statue);

		// ~~~~~~~~~~~~~~~~ Ajouts d'objets dans la pièce 23:

		PlayerEvent playerEvt = (PlayerEvent & Serializable)
				(Player player) -> player.getRoom().getLockedDoor("door22").unlock(new Pass("passC", "passC", PassType.C));
		Event unlockEvt = new Event("unlock", "unlock the door", playerEvt);
		Computer comp = new Computer("The lab computer", "computer", unlockEvt, new Scalar2D(8, 8));

		File file1 = new File("doctorLog", "Evidence of lab experiments on humans.",
				"""
						We have been abducting humans for the past few years now.
						We have been conducting all sorts of experiments on these primates. We were tasked to understand how their immune system works, but the Commander refuses to tell us more.
						I hope he's not planning anything too bad, it would be a shame to lose such efficient guinea pigs.""");
		File file2 = new File("importantMessage", "A message addressed to all Scientists by Commander Gelgax",
				"Attention to all scientists. A new human has arrived yesterday. " +
						"Ready him as soon as possible for the next lab tests. Insubordination will not be tolerated.");
		File file3 = new File("recipe", "A recipe from the lab computer",
				"""
						Step 1
						\tPreheat oven to 350 degrees F (180 degrees C).
						Step 2
						\tCream butter and sugar until fluffy. Stir in vanilla; add flour and mix well.
						Step 3
						\tPut through cookie press and form cookies onto baking sheets. Bake for 10 - 12 minutes.
						""");

		comp.addFile(file1);
		comp.addFile(file2);
		comp.addFile(file3);

		room23.getInventory().addItem(comp);

		// ~~~~~~~~~~~~~~~~ Ajouts d'objets dans la pièce 24:
		File plunger = new File("plunger", "A... Man-made plunger? What is it even doing here?",
				"""
						Within all this apparent luxury you found an abnormal object : a plunger between two splendid Statues.
						Some aliens seem quite interested in the Human culture...  maybe too much interested.
						It just feels wrong here.""");
		room24.getInventory().addItem(plunger);

		// ~~~~~~~~~~~~~~~~ Ajouts d'objets dans la pièce 28:

		File Datapad = new File("Datapad", "A DataPad from the Lab.",
				"""
						Seems like someone has forgotten to lock their DataPad. I tried to read it, but some files are just way too complicated for me.
						I only managed to get that they're using humans as guinea pigs, in order to develop biological weapons so that they could invade earth.
						What a crazy plan... It could be a good story for a video-game!""");
		room28.getInventory().addItem(Datapad);


		// =================================================================================================== //
		// =================================== Ajout des pièces du vaisseau ================================== //
		// =================================================================================================== //
		ROOMS.put(room11.getID(), room11);
		ROOMS.put(room13.getID(), room13);
		ROOMS.put(room14.getID(), room14);

		ROOMS.put(room17.getID(), room17);
		ROOMS.put(room18.getID(), room18);
		ROOMS.put(room19.getID(), room19);

		ROOMS.put(room21.getID(), room21);
		ROOMS.put(room22.getID(), room22);
		ROOMS.put(room23.getID(), room23);

		ROOMS.put(room24.getID(), room24);
		ROOMS.put(room27.getID(), room27);
		ROOMS.put(room28.getID(), room28);

		ROOMS.put(room29.getID(), room29);
		ROOMS.put(room30.getID(), room30);
	}

	public Ship(Ship ship){
		this.ROOMS = ship.ROOMS;
		this.PLAYER = ship.PLAYER;
		this.npcs = ship.npcs;
	}

	public NPC getNPC(String s)
	{
		return this.npcs.get(s);
	}

	public Player getPlayer()
	{
		return this.PLAYER;
	}

	public Room getRoom(int id)
	{
		return this.ROOMS.get(id);
	}
}
