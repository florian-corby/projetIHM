package controller.Location;

import model.Location.Ship;
import controller.Characters.NPC;
import controller.Characters.Player;
import controller.Characters.Umhon;
import controller.Doors.Door;
import controller.Doors.LockedDoor;
import controller.Events.Event;
import controller.Events.PlayerEvent;
import controller.Items.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipController implements Serializable {

	private model.Location.Ship shipModel = new model.Location.Ship();

	public Ship getShipModel() {
		return shipModel;
	}
}
