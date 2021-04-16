package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

import java.util.LinkedHashMap;

public class RoomView extends GridPane {

    private final LinkedHashMap<String, Shape> gameElementViews = new LinkedHashMap<>();;

    public RoomView(int nbCol, int nbLignes)
    {
        initRoom(nbCol, nbLignes);
        initStyle();
    }

    public void addInRoom(Shape gameElementView, String viewTag, int colIndex, int ligneIndex)
    {
        gameElementViews.put(viewTag, gameElementView);
        this.add(gameElementView, colIndex, ligneIndex);
    }

    public Shape getFromRoom(String viewTag) { return gameElementViews.get(viewTag); }
    public String getTag(Shape shape)
    {
        for (String tag : gameElementViews.keySet()) {
            if (gameElementViews.get(tag).equals(shape)) {
                return tag;
            }
        }
        return null;
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
        //this.setGridLinesVisible(true); //Décommenter cette ligne pour voir les cases du jeu
        this.setStyle("-fx-background-color:white; -fx-border-color:black;");
    }
}
