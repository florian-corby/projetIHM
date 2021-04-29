package silent_in_space.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DoorView extends Rectangle
{
    public DoorView(String catDoor) {
        setDoorViewColor(catDoor);
    }

    // Détermination de l'orientation d'un porte
    public String getAlignment(int[] roomSize, int[] doorPos) {
        if(doorPos[0] == 0)
            return "LEFT";
        else if(doorPos[0] == roomSize[0]-1)
            return "RIGHT";
        else if(doorPos[1] == 0)
            return "TOP";
        else
            return "BOTTOM";
    }

    // Vue de la porte
    public void setDoorGeometry(int[] roomSize, int[] doorPos){
        if(doorPos[0] == 0 || doorPos[0] == roomSize[0]-1){
            setWidth(10);
            setHeight(25);
        }
        else{
            setWidth(25);
            setHeight(10);
        }
    }

    // Changement de couleur suivant la catégorie de la porte
    public void setDoorViewColor(String catDoor) {
        switch (catDoor) {
            case "normal" -> setFill(Color.BLACK);
            case "locked" -> setFill(Color.RED);
            case "code" -> setFill(Color.LIME);
            default -> setFill(Color.LIGHTGRAY);
        }
    }
}
