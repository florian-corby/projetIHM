package model.Game;

import model.Location.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Scanner;

public class SIS implements Serializable {

	private Ship ship;

	public SIS()
	{
		this.initGame();
		this.play();
		this.endGame();
	}

	public void initGame()
	{
		this.printGameIntro();
		this.printScenario();

		Scanner scan = new Scanner(System.in);
		System.out.println("Load an existing game?\n(Type \"yes\" if you have a save file. Press Enter for a new game.)");
		System.out.print("\nCommand :> ");
		String userChoice = scan.nextLine();

		if (userChoice.equals("yes")) {
			try {
				FileInputStream fileIn = new FileInputStream("saveData.txt");
				ObjectInputStream ois = new ObjectInputStream(fileIn);
				this.ship = new Ship((Ship) ois.readObject());
				ois.close();
				System.out.println("You successfully loaded the game!\n");
				System.out.println("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("No save data was found! You need to save at least one time before being able to load a save.");
				System.out.println("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");

				System.out.println("You wake up feeling dizzy. Something is talking to you. Something not human.\n");
				this.ship = new Ship();
				this.ship.getNPC("Kilen").talk();
				this.ship.getNPC("Kilen").give("passT", this.ship.getPlayer());
				this.ship.getNPC("Kilen").setSpeech("You should hurry! I've managed to deal with the guards in the lab but it won't be long before they come back!");
			}
		} else {
			System.out.println("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
			System.out.println("You wake up feeling dizzy. Something is talking to you. Something not human.\n");
			this.ship = new Ship();
			this.ship.getNPC("Kilen").talk();
			this.ship.getNPC("Kilen").give("passT", this.ship.getPlayer());
			this.ship.getNPC("Kilen").setSpeech("You should hurry! I've managed to deal with the guards in the lab but it won't be long before they come back!");
		}

		this.ship.getPlayer().setSIS(this);
	}

	public boolean isEndGame()
	{
		return (
				(this.ship.getRoom(13).hasActor("me")
						&& this.ship.getPlayer().getInventory().getItem("CaptainCode") != null)
						|| this.ship.getPlayer().isDead()
		);
	}

	public void endGame()
	{
		System.out.println("\n\n\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
		System.out.println("Thanks for playing Silent In Space! And special thanks to our beta-tester Ophélie De Sousa Oliveira :) !\n");
	}

	public void load()
	{
		try {
			FileInputStream fileIn = new FileInputStream("saveData.txt");
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			this.ship = new Ship((Ship) ois.readObject());
			ois.close();
			System.out.println("You successfully loaded the game!\n");
			System.out.println("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("No save data was found! You need to save at least one time before being able to load a save.");
			System.out.println("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
		}
	}

	public void play()
	{
		while(!this.isEndGame())
		{
			this.playTurn();
		}

		if(this.ship.getNPC("Umhon").isDead())
			System.out.println("\nYou managed to escape but Umhon couldn't stop her husband and his commander. Thus, the aliens" +
					" managed to create a virus which decimated all the human population. You were the last survivor and witnessed " +
					"the fruits of your own actions.");

		else
			System.out.println("\nYou managed to escape and Umhon stopped the experiments on the human beings. Instead, the aliens chose a more " +
					"diplomatic way to settle down on Earth by reaching out to the European Space Agency of Toulouse-Matabiau.");
	}

	public void playTurn()
	{
		this.ship.getPlayer().call();
	}

	public void printGameIntro()
	{
		System.out.println("\t\t ========================================= ");
		System.out.println("\t\t ============ SILENT IN SPACE ============ ");
		System.out.println("\t\t ========================================= ");

		System.out.println("\nWELCOME to Silent In Space! This game was developed by Florian Legendre, Alexis Louail\n" +
				"and Vincent Tourenne as a universitary project. This is a demo, hence all the features\nintended to be " +
				"in the final version aren't there. This game is meant to be played by\ntextual commands. Meaning that " +
				"you must input valid commands with your keyboard and\nthe game will react accordingly. For a thorough " +
				"listing of commands, their syntaxes\nand effects, type help! Enjoy!\n");
	}

	public void printScenario()
	{
		System.out.println("SCENARIO: You wake up in an alien ship. You understand that you've been abducted and\nyou " +
				"must escape. Yet, you can't use the escape pods of the ship without a code.\nUmhon, an important alien " +
				"person, can give you this code (OR you can take it from her\nbody) but you have to bring her the proof of " +
				"the abominable experiments being conducted\non humans. This proof is what will end the abductions and " +
				"possibly the end of humanity.\nThe escape room is ROOM 13. Good luck human!\n");
	}
}