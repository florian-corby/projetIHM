package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DoorView extends Rectangle
{
    public DoorView(String catDoor) {
        setWidth(10);
        setHeight(25);
        setDoorViewColor(catDoor);
    }

    public void setDoorViewColor(String catDoor) {
        switch (catDoor) {
            case "normal" -> setFill(Color.BLACK);
            case "locked" -> setFill(Color.RED);
            case "code" -> setFill(Color.LIME);
            default -> setFill(Color.LIGHTGRAY);
        }
    }

    public String getAlignment(int[] roomSize, int[] doorPos)
    {
        if(doorPos[0] == 0)
            return "LEFT";
        else if(doorPos[0] == roomSize[0]-1)
            return "RIGHT";
        else if(doorPos[1] == 0)
            return "TOP";
        else
            return "BOTTOM";
    }
}
