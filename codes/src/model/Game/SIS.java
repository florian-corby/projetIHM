package model.Game;

import model.Location.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Scanner;

public class SIS implements Serializable {

	private Ship ship;

	public SIS() {
		this.initGame();
		this.play();
		this.endGame();
	}

	public SIS(MessageListener messageListener) {
		Message.setMessageListener(messageListener);
		this.initGame();
	}

	public Ship getShip() {
		return ship;
	}

	public void initGame() {
		/*Scanner scan = new Scanner(System.in);
		Message.sendGameMessage("Load an existing game?\n(Type \"yes\" if you have a save file. Press Enter for a new game.)");
		Message.sendGameMessage("\nCommand :> ");
		String userChoice = scan.nextLine();

		if (userChoice.equals("yes")) {
			try {
				FileInputStream fileIn = new FileInputStream("saveData.txt");
				ObjectInputStream ois = new ObjectInputStream(fileIn);
				this.ship = new Ship((Ship) ois.readObject());
				ois.close();
				Message.sendGameMessage("You successfully loaded the game!\n");
				Message.sendGameMessage("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
			} catch (IOException | ClassNotFoundException e) {
				Message.sendGameMessage("No save data was found! You need to save at least one time before being able to load a save.");
				Message.sendGameMessage("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");

				Message.sendGameMessage("You wake up feeling dizzy. Something is talking to you. Something not human.\n");
				this.ship = new Ship();
				this.ship.getNPC("Kilen").talk();
				this.ship.getNPC("Kilen").give("passT", this.ship.getPlayer());
				this.ship.getNPC("Kilen").setSpeech("You should hurry! I've managed to deal with the guards in the lab but it won't be long before they come back!");
			}
		} else {*/
			Message.addGameMessage("You wake up feeling dizzy. Something is talking to you. Something not human.\n\n");
			this.ship = new Ship();
			this.ship.getNPC("Kilen").talk();
			this.ship.getNPC("Kilen").give("passT", this.ship.getPlayer());
			this.ship.getNPC("Kilen").setSpeech("You should hurry! I've managed to deal with the guards in the lab but it won't be long before they come back!");
		//}

		this.ship.getPlayer().setSIS(this);
	}

	public boolean isEndGame() {
		return (
				(this.ship.getRoom(13).hasActor("me")
						&& this.ship.getPlayer().getInventory().getItem("CaptainCode") != null)
						|| this.ship.getPlayer().isDead()
		);
	}

	public void endGame() {
		Message.addGameMessage("\n\n\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
		Message.sendGameMessage("Thanks for playing Silent In Space! And special thanks to our beta-tester Ophélie De Sousa Oliveira :) !\n");
	}

	public void load() {
		try {
			FileInputStream fileIn = new FileInputStream("saveData.txt");
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			this.ship = new Ship((Ship) ois.readObject());
			ois.close();
			Message.addGameMessage("You successfully loaded the game!\n");
			Message.sendGameMessage("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
		} catch (IOException | ClassNotFoundException e) {
			Message.addGameMessage("No save data was found! You need to save at least one time before being able to load a save.");
			Message.sendGameMessage("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
		}
	}

	public void play() {
		while(!this.isEndGame())
		{
			this.playTurn();
		}

		if(this.ship.getNPC("Umhon").isDead())
			Message.sendGameMessage("\nYou managed to escape but Umhon couldn't stop her husband and his commander. Thus, the aliens" +
					" managed to create a virus which decimated all the human population. You were the last survivor and witnessed " +
					"the fruits of your own actions.");

		else
			Message.sendGameMessage("\nYou managed to escape and Umhon stopped the experiments on the human beings. Instead, the aliens chose a more " +
					"diplomatic way to settle down on Earth by reaching out to the European Space Agency of Toulouse-Matabiau.");
	}

	public void playTurn()
	{
		this.ship.getPlayer().call();
	}

	public void printHelp() {
		Message.addGameMessage("--- SILENT IN SPACE --- \n");
		Message.addGameMessage("""
				WELCOME to Silent In Space! This game was developed by Florian Legendre, Alexis Louail
				and Vincent Tourenne as a universitary project. This is a demo, hence all the features
				intended to be in the final version aren't there. This game is meant to be played by
				textual commands. Meaning that you must input valid commands with your keyboard and
				the game will react accordingly. For a thorough listing of commands, their syntaxes
				and effects, type help! Enjoy!

				""");
		Message.sendGameMessage("""
				SCENARIO: You wake up in an alien ship. You understand that you've been abducted and
				you must escape. Yet, you can't use the escape pods of the ship without a code.
				Umhon, an important alien person, can give you this code (OR you can take it from her
				body) but you have to bring her the proof of the abominable experiments being conducted
				on humans. This proof is what will end the abductions and possibly the end of humanity.
				The escape room is ROOM 13. Good luck human!

				""");
	}
}