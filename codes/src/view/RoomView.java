package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
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
    public RoomView(int nbCol, int nbLignes) {
        availablePos = new boolean[nbCol][nbLignes];
        this.nbCol = nbCol;
        this.nbLignes = nbLignes;

        initRoom(nbCol, nbLignes);
        initStyle();
    }

    // Création de pièce
    private void initRoom(int nbCol, int nbLignes) {
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

    private void initStyle() {
        //setGridLinesVisible(true); //Décommenter cette ligne pour voir les cases du jeu
        setStyle("-fx-background-color:white; -fx-border-color:black;");
    }

    // ============================== GETTERS =========================================
    public LinkedHashMap<String, Shape> getGameElementViews() { return gameElementViews; }
    public Shape getFromRoom(String viewTag) { return gameElementViews.get(viewTag); }
    public int getNbCol() { return nbCol; }
    public int getNbLignes() { return nbLignes; }


    public String getTag(Shape shape) {
        for (String tag : gameElementViews.keySet()) {
            if (gameElementViews.get(tag).equals(shape)) {
                return tag;
            }
        }
        return null;
    }

    public int[] getRandPos() {
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

    // Ajout d'un élement dans la pièce a un endroit precis avec un tag
    public void addInRoom(Shape gameElementView, String viewTag, int colIndex, int ligneIndex, String align) {
        gameElementViews.put(viewTag, gameElementView);
        alignInRoom(gameElementView, align);
        add(gameElementView, colIndex, ligneIndex);
        availablePos[colIndex][ligneIndex] = false;
    }

    // Suppresion d'un element dans la pièce grace à un tag
    public void removeFromRoom(String viewTag) {
        Node nodeToRemove = gameElementViews.get(viewTag);
        int colIndex = GridPane.getRowIndex(nodeToRemove);
        int rowIndex = GridPane.getColumnIndex(nodeToRemove);

        getChildren().remove(gameElementViews.get(viewTag));
        gameElementViews.remove(viewTag);
        availablePos[colIndex][rowIndex] = true;
    }


    // ================================= OTHERS =========================================

    // Fonction gérant l'alignement dans une piece
    public void alignInRoom(Node nodeToAlign, String align) {
        switch (align) {
            case "TOP" -> GridPane.setValignment(nodeToAlign, VPos.TOP);
            case "RIGHT" -> GridPane.setHalignment(nodeToAlign, HPos.RIGHT);
            case "BOTTOM" -> GridPane.setValignment(nodeToAlign, VPos.BOTTOM);
            case "LEFT" -> GridPane.setHalignment(nodeToAlign, HPos.LEFT);
            default -> {
                GridPane.setValignment(nodeToAlign, VPos.CENTER);
                GridPane.setHalignment(nodeToAlign, HPos.CENTER);
            }
        }
    }
}
