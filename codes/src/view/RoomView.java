package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class RoomView extends GridPane {

    public RoomView(int nbCol, int nbLignes)
    {
        initRoom(nbCol, nbLignes);
        initStyle();
    }

    private void initRoom(int nbCol, int nbLignes)
    {
        for(int i = 0; i < nbCol; i++)
        {
            for(int j = 0; j < nbLignes; j++) {
                Region ressort = new Region();
                ressort.setMinSize(25, 25);
                ressort.setMaxSize(25, 25);
                this.add(ressort, i, j);
            }
        }
    }

    private void initStyle()
    {
        this.setGridLinesVisible(true);
        this.setStyle("-fx-background-color:white;");
    }
}
