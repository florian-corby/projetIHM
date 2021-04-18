package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DoorView extends Rectangle
{
    private DoorView(String catDoor)
    {
        setWidth(10);
        setHeight(25);
        setDoorViewColor(catDoor);
    }

    public DoorView(String catDoor, HPos wallToStick)
    {
        this(catDoor);
        GridPane.setHalignment(this, wallToStick);
    }

    public DoorView(String catDoor, VPos wallToStick)
    {
        this(catDoor);
        GridPane.setValignment(this, wallToStick);
    }

    public void setDoorViewColor(String catDoor)
    {
        switch (catDoor) {
            case "normal" -> setFill(Color.BLACK);
            case "locked" -> setFill(Color.RED);
            case "code" -> setFill(Color.LIME);
            default -> setFill(Color.LIGHTGRAY);
        }
    }
}
