package controller.Game;

import controller.Location.ShipController;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Scanner;

public class SISController implements Serializable {

	private ShipController ship;
	private final TextArea dialogTextField;

	public SISController(TextArea dialogTextField)
	{
		this.dialogTextField = dialogTextField;
		this.initGame();
		//this.play();
		//this.endGame();
	}

	public ShipController getShip()
	{
		return ship;
	}

	public void initGame()
	{
		this.printGameIntro();
		this.printScenario();

		dialogTextField.setText("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
		dialogTextField.setText("You wake up feeling dizzy. Something is talking to you. Something not human.\n");
		ship = new ShipController();
		ship.getShipModel().getNPC("Kilen").talk();
		ship.getShipModel().getNPC("Kilen").give("passT", ship.getShipModel().getPlayer());
		ship.getShipModel().getNPC("Kilen").setSpeech("You should hurry! I've managed to deal with the guards in the lab but it won't be long before they come back!");

		//ship.getShipModel().getPlayer().setSIS(this);
	}

	public boolean isEndGame()
	{
		return (
				(ship.getShipModel().getRoom(13).hasActor("me")
						&& ship.getShipModel().getPlayer().getInventory().getItem("CaptainCode") != null)
						|| ship.getShipModel().getPlayer().isDead()
		);
	}

	public void endGame()
	{
		dialogTextField.setText("\n\n\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n");
		dialogTextField.setText("Thanks for playing Silent In Space! And special thanks to our beta-tester Ophélie De Sousa Oliveira :) !\n");
	}

	/*public void load()
	{
		try {
			FileInputStream fileIn = new FileInputStream("saveData.txt");
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			ship = new ShipController((Ship) ois.readObject());
			ois.close();
			dialogTextField.setText("You successfully loaded the game!\n");
			dialogTextField.setText("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
		} catch (IOException | ClassNotFoundException e) {
			dialogTextField.setText("No save data was found! You need to save at least one time before being able to load a save.");
			dialogTextField.setText("\t\t ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n\n");
		}
	}*/

	/*public void play()
	{
		while(!this.isEndGame())
		{
			this.playTurn();
		}

		if(ship.getShipModel().getNPC("Umhon").isDead())
			dialogTextField.setText("\nYou managed to escape but Umhon couldn't stop her husband and his commander. Thus, the aliens" +
					" managed to create a virus which decimated all the human population. You were the last survivor and witnessed " +
					"the fruits of your own actions.");

		else
			dialogTextField.setText("\nYou managed to escape and Umhon stopped the experiments on the human beings. Instead, the aliens chose a more " +
					"diplomatic way to settle down on Earth by reaching out to the European Space Agency of Toulouse-Matabiau.");
	}*/

	/*public void playTurn()
	{
		ship.getShipModel().getPlayer().call();
	}*/

	public void printGameIntro()
	{
		dialogTextField.setText("\t\t ========================================= ");
		dialogTextField.setText("\t\t ============ SILENT IN SPACE ============ ");
		dialogTextField.setText("\t\t ========================================= ");

		dialogTextField.setText("\nWELCOME to Silent In Space! This game was developed by Florian Legendre, Alexis Louail\n" +
				"and Vincent Tourenne as a universitary project. This is a demo, hence all the features\nintended to be " +
				"in the final version aren't there. This game is meant to be played by\ntextual commands. Meaning that " +
				"you must input valid commands with your keyboard and\nthe game will react accordingly. For a thorough " +
				"listing of commands, their syntaxes\nand effects, type help! Enjoy!\n");
	}

	public void printScenario()
	{
		dialogTextField.setText("SCENARIO: You wake up in an alien ship. You understand that you've been abducted and\nyou " +
				"must escape. Yet, you can't use the escape pods of the ship without a code.\nUmhon, an important alien " +
				"person, can give you this code (OR you can take it from her\nbody) but you have to bring her the proof of " +
				"the abominable experiments being conducted\non humans. This proof is what will end the abductions and " +
				"possibly the end of humanity.\nThe escape room is ROOM 13. Good luck human!\n");
	}
}