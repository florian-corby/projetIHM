package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;

import java.util.LinkedHashMap;
import java.util.Random;

public class RoomView extends GridPane {

    private final LinkedHashMap<String, Shape> gameElementViews = new LinkedHashMap<>();
    private boolean[][] availablePos;
    private final int nbCol;
    private final int nbLignes;
    private final Random randGen = new Random();

    // ======================== CONSTRUCTORS/INITIALIZERS ============================
    public RoomView(int nbCol, int nbLignes)
    {
        availablePos = new boolean[nbCol][nbLignes];
        this.nbCol = nbCol;
        this.nbLignes = nbLignes;

        initRoom(nbCol, nbLignes);
        initStyle();
    }

    private void initRoom(int nbCol, int nbLignes)
    {
        for(int i = 0; i < nbCol; i++) {
            for(int j = 0; j < nbLignes; j++) {
                Region ressort = new Region();
                ressort.setMinSize(25, 25);
                ressort.setMaxSize(25, 25);
                add(ressort, i, j);
                availablePos[i][j] = true;
            }
        }

        availablePos[nbCol/2][nbLignes/2] = false;
    }

    private void initStyle()
    {
        setGridLinesVisible(true); //Décommenter cette ligne pour voir les cases du jeu
        setStyle("-fx-background-color:white; -fx-border-color:black;");
    }


    // ============================== GETTERS =========================================
    public Shape getFromRoom(String viewTag) { return gameElementViews.get(viewTag); }
    public int getNbCol() { return nbCol; }
    public int getNbLignes() { return nbLignes; }

    public String getTag(Shape shape)
    {
        for (String tag : gameElementViews.keySet()) {
            if (gameElementViews.get(tag).equals(shape)) {
                return tag;
            }
        }
        return null;
    }

    public int[] getRandPos()
    {
        while(true) {
            int col = randGen.nextInt(nbCol);
            int ligne = randGen.nextInt(nbLignes);
            if (availablePos[col][ligne]) {
                return new int[]{col, ligne};
            }
        }
    }


    // ============================== PREDICATS ========================================
    public boolean isAvailablePos(int colIndex, int LigneIndex) { return availablePos[colIndex][LigneIndex]; }


    // =========================== SETTERS/UNSETTERS ===================================
    public void addInRoom(Shape gameElementView, String viewTag, int colIndex, int ligneIndex)
    {
        gameElementViews.put(viewTag, gameElementView);
        add(gameElementView, colIndex, ligneIndex);
        availablePos[colIndex][ligneIndex] = false;
    }

    public void removeFromRoom(String viewTag)
    {
        getChildren().remove(gameElementViews.get(viewTag));
        gameElementViews.remove(viewTag);
    }


    // =============================== UPDATERS ========================================
}
