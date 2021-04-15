package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Game.SIS;
import model.Location.Room;
import view.MainPanelView;
import view.RoomView;

import java.io.IOException;

public class GameController {

    private final MainPanelView mainPanelView;
    private final SIS gameModel;
    private RoomView currentRoom;
    private Circle playerView;

    public GameController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPanel.fxml"));
        loader.load();
        mainPanelView = loader.getController();
        gameModel = new SIS(mainPanelView);
        playerView = new Circle();
        playerView.setRadius(10);
        playerView.setFill(Color.BLUE);

        initHandlers();
        initHelpManual();
        initTestRoom();
    }

    public void initHandlers()
    {
        playerView.setOnMouseClicked(e -> { mainPanelView.update("It's a me! Mario! Wouhou!");});
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

        mainPanelView.setHelpMessage(helpDialog);
    }

    public void initRoom(int nbCol, int nbLignes)
    {
        RoomView roomView = new RoomView(nbCol, nbLignes);
        currentRoom = roomView;
        mainPanelView.getMapPane().getChildren().add(currentRoom);
    }

    public void initTestRoom()
    {
        Room playerRoom = gameModel.getShip().getPlayer().getRoom();
        initRoom(11, 11);

        GridPane.setHalignment(playerView, HPos.CENTER);
        GridPane.setValignment(playerView, VPos.CENTER);
        currentRoom.add(playerView, 5, 5);
    }

    public HBox getScene() {
        return mainPanelView.getScene();
    }
}
