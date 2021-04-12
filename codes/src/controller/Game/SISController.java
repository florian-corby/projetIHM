package controller.Game;

import controller.Location.ShipController;
import javafx.scene.control.TextArea;
import java.io.Serializable;

public class SISController implements Serializable {

	private ShipController ship;
	private final TextArea dialogTextField;

	public SISController(TextArea dialogTextField)
	{
		this.dialogTextField = dialogTextField;
		this.initGame();
	}

	public ShipController getShip()
	{
		return ship;
	}

	public void initGame()
	{
		dialogTextField.setText("You wake up feeling dizzy. Something is talking to you. Something not human.\n");
		ship = new ShipController();
		ship.getShipModel().getNPC("Kilen").talk();
		ship.getShipModel().getNPC("Kilen").give("passT", ship.getShipModel().getPlayer());
		ship.getShipModel().getNPC("Kilen").setSpeech("You should hurry! I've managed to deal with the guards in the lab but it won't be long before they come back!");
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
		dialogTextField.setText("\n\n\t\t ~~~~~~~~~~~ \n");
		dialogTextField.setText("Thanks for playing Silent In Space! And special thanks to our beta-tester Ophélie De Sousa Oliveira :) !\n");
	}
}