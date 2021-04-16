package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import model.Game.SIS;
import view.GameView;
import view.RoomView;

import java.io.IOException;

public class GameController {

    //====================== ATTRIBUTS ==========================
    private final SIS gameModel;
    private final GameView gameView;
    private RoomView currentRoomView;
    private Circle playerView;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public GameController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameView.fxml"));
        loader.load();
        gameView = loader.getController();
        gameModel = new SIS(gameView);

        initPlayerView();
        initHelpManual();
        initTestRoom();
        initHandlers();
    }

    public void initHandlers()
    {
        playerView.setOnMouseClicked(e -> { gameView.update("It's a me! Mario! Wouhou!");});
    }

    public void initHelpManual()
    {
        String helpDialog = """
                -- User Manual of Silent In Space --\s

                WELCOME to Silent In Space! This game was developed by Florian Legendre, Alexis Louail
                and Vincent Tourenne as a universitary project. This is a demo, hence all the features
                intended to be in the final version aren't there. This game is meant to be played by
                textual commands. Meaning that you must input valid commands with your keyboard and
                the game will react accordingly. For a thorough listing of commands, their syntaxes
                and effects, type help! Enjoy!

                SCENARIO: You wake up in an alien ship. You understand that you've been abducted and
                you must escape. Yet, you can't use the escape pods of the ship without a code.
                Umhon, an important alien person, can give you this code (OR you can take it from her
                body) but you have to bring her the proof of the abominable experiments being conducted
                on humans. This proof is what will end the abductions and possibly the end of humanity.
                The escape room is ROOM 13. Good luck human!

                """;

        gameView.setHelpMessage(helpDialog);
    }

    public void initPlayerView()
    {
        playerView = new Circle();
        playerView.setRadius(10);
        playerView.setFill(Color.BLUE);
    }

    public void initTestRoom()
    {
        updateRoomView(11, 11);

        //Ajout de la porte T:
        Rectangle door = new Rectangle(10, 40);
        door.setFill(Color.RED);
        GridPane.setHalignment(door, HPos.RIGHT);
        currentRoomView.add(door, 10, 5);

        //Ajout de l'alien:
        Circle alien = new Circle();
        alien.setFill(Color.LIME);
        alien.setRadius(10);
        currentRoomView.add(alien, 8, 3);

        //Ajout du conteneur de santé:
        Polygon healthContainer = new Polygon();
        healthContainer.getPoints().addAll(20.0, 10.0, 40.0, 10.0, 45.0, 15.0, 40.0, 25.0, 20.0, 25.0, 15.0, 15.0);
        healthContainer.setFill(Color.MAGENTA);
        currentRoomView.add(healthContainer, 9, 8);
    }

    //====================== GETTERS ==========================
    public HBox getScene() {
        return gameView.getScene();
    }

    //====================== UPDATERS =========================
    public void updateRoomView(int nbCol, int nbLignes)
    {
        currentRoomView = new RoomView(nbCol, nbLignes);

        //On centre le joueur dans la pièce:
        currentRoomView.add(playerView, (nbCol - 1)/2, (nbLignes-1)/2);
        gameView.getMapHBox().getChildren().add(currentRoomView);
    }
}
