package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import model.Characters.Player;
import model.Game.SIS;
import model.Location.Room;
import view.GameView;
import view.RoomView;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameController {

    //====================== ATTRIBUTS ==========================
    private final SIS gameModel;
    private final GameView gameView;
    private Room currentRoomModel;
    private RoomView currentRoomView;
    private Circle playerView;
    private Player playerModel;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public GameController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameView.fxml"));
        loader.load();
        gameView = loader.getController();
        gameModel = new SIS(gameView);
        playerModel = gameModel.getShip().getPlayer();
        currentRoomModel = playerModel.getRoom();

        initPlayerView();
        initHelpManual();
        initTestRoom();
        initHandlers();
        initPlayer();
    }

    public void initHandlers()
    {
/*
        // ========== ALIEN
        currentRoomView.getFromRoom("Alien").setOnMousePressed(e -> {
            if(e.isPrimaryButtonDown())
            gameView.getActorImageView().setImage(new Image("../../libraries/img/alien"));
                });
*/

        // ========== BACK BUTTON
        gameView.getBackButton().setOnMouseClicked(e-> {
            playerModel.back();
            gameView.update("You pressed the back button.");
        });

        // ========== HEALTH STATION
        currentRoomView.getFromRoom("HealthStation").setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                gameView.update(currentRoomModel.getInventory().getItem("HealthStation").getDescription());
        });

        // ========== SAVE & LOAD BUTTON
        gameView.getSaveButton().setOnMouseClicked(e-> {
            playerModel.save();
            gameView.update("You successfully saved the game!");
        });

        gameView.getLoadButton().setOnMouseClicked(e-> {
            playerModel.load();
            gameView.update("You successfully loaded the game!");
        });


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
        currentRoomView.addInRoom(door, currentRoomModel.getDoor(0).getTag(), 10, 5);

        //Ajout de l'alien:
        Circle alien = new Circle();
        alien.setFill(Color.LIME);
        alien.setRadius(10);
        currentRoomView.addInRoom(alien, currentRoomModel.getNPCTag(0), 8, 3);

        //Ajout du conteneur de santé:
        Polygon healthStation = new Polygon();
        healthStation.getPoints().addAll(82.0, 32.0, 86.0, 26.0, 94.0, 26.0,
                98.0, 32.0, 94.0, 40.0, 86.0, 40.0);
        healthStation.setFill(Color.MAGENTA);
        currentRoomView.addInRoom(healthStation, currentRoomModel.getInventory().getItemTag(0), 9, 8);

        //Ajout de la statuette:
        Ellipse statuette = new Ellipse();
        statuette.setRadiusX(5);
        statuette.setRadiusY(10);
        statuette.setFill(Color.BLACK);
        currentRoomView.addInRoom(statuette, currentRoomModel.getInventory().getItemTag(1), 2, 2);
    }

    public void initPlayer()
    {
        gameView.setPlayer(this.playerModel);
    }

    //====================== GETTERS ==========================
    public HBox getScene() {
        return gameView.getScene();
    }

    //====================== UPDATERS =========================
    public void updateRoomView(int nbCol, int nbLignes)
    {
        currentRoomView = new RoomView(nbCol, nbLignes);

        //On met à jour le label de la map:
        gameView.getRoomLabel().setText("Room " + currentRoomModel.getID());

        //On centre le joueur dans la pièce:
        currentRoomView.add(playerView, (nbCol - 1)/2, (nbLignes-1)/2);
        gameView.getMapHBox().getChildren().add(currentRoomView);

        //On met à jour les handlers de description:
        playerView.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                gameView.update(playerModel.getName());
        });
    }
}
