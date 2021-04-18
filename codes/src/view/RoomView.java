package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

import java.util.LinkedHashMap;

public class RoomView extends GridPane {

    private final LinkedHashMap<String, Shape> gameElementViews = new LinkedHashMap<>();

    // ======================== CONSTRUCTORS/INITIALIZERS ============================
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
                add(ressort, i, j);
            }
        }
    }

    private void initStyle()
    {
        //setGridLinesVisible(true); //Décommenter cette ligne pour voir les cases du jeu
        setStyle("-fx-background-color:white; -fx-border-color:black;");
    }


    // ============================== GETTERS =========================================
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

    // =========================== SETTERS/UNSETTERS ===================================
    public void addInRoom(Shape gameElementView, String viewTag, int colIndex, int ligneIndex)
    {
        gameElementViews.put(viewTag, gameElementView);
        add(gameElementView, colIndex, ligneIndex);
    }

    public void removeFromRoom(String viewTag)
    {
        getChildren().remove(gameElementViews.get(viewTag));
        gameElementViews.remove(viewTag);
    }


    // =============================== UPDATERS ========================================
}
