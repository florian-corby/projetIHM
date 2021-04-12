package controller.Location;

import model.Location.Ship;

import java.io.Serializable;

public class ShipController implements Serializable {

	private model.Location.Ship shipModel = new model.Location.Ship();

	public Ship getShipModel() {
		return shipModel;
	}
}
