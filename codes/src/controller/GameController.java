package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import model.Characters.Actor;
import model.Characters.Player;
import model.Game.SIS;
import model.Items.Item;
import model.Location.Room;
import view.*;

import java.io.IOException;
import java.util.Random;

public class GameController {

    //====================== ATTRIBUTS ==========================
    private final SIS gameModel;
    private final GameView gameView;
    private Room currentRoomModel;
    private RoomView currentRoomView;
    private final ActorView playerView;
    private final Player playerModel;

    //Gestion du manuel d'aide:
    private String previousDialog;
    private Boolean isHelpManualOn = false;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public GameController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameView.fxml"));
        loader.load();
        gameView = loader.getController();
        gameModel = new SIS(gameView);
        playerView = new ActorView("player");
        playerModel = gameModel.getShip().getPlayer();
        currentRoomModel = playerModel.getRoom();
        previousDialog = gameView.getDialogTextArea().getText();

        initTestRoom();
        initHandlers();
    }

    public void initHandlers()
    {
        currentRoomView.getFromRoom("HealthStation").setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                gameView.update(currentRoomModel.getInventory().getItem("HealthStation").getDescription());
        });

        gameView.getHelpButton().setOnAction(e -> {
            if(isHelpManualOn) {
                isHelpManualOn = false;
                gameView.getHelpButton().setText("?");
                gameView.getDialogTextArea().setText(previousDialog);
            }

            else {
                isHelpManualOn = true;
                previousDialog = gameView.getDialogTextArea().getText();
                gameView.getHelpButton().setText("Back to the Game");
                gameModel.printHelp();
            }
        });
    }

    public void initTestRoom()
    {
        updateRoomView(11, 11);

        //Ajout de la porte T:
        DoorView doorView = new DoorView("locked", HPos.RIGHT);
        currentRoomView.addInRoom(doorView, currentRoomModel.getDoor(0).getTag(), 10, 5);

        //Ajout du conteneur de santé:
        //ContainerView healthStation = new ContainerView("HealthStation");
        //currentRoomView.addInRoom(healthStation, currentRoomModel.getInventory().getItemTag(0), 9, 8);
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

        //On place le joueur au centre de la pièce:
        currentRoomView.addInRoom(playerView, playerModel.getName(), (nbCol - 1)/2, (nbLignes-1)/2);
        gameView.getMapHBox().getChildren().add(currentRoomView);

        //On place les objets de la pièce:
        Item[] items = currentRoomModel.getInventory().getItems();
        for (Item item : items) {
            ItemView itemView = new ItemView();
            itemView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown())
                    gameView.update(item.getDescription());
            });
            currentRoomView.addInRoom(itemView, item.getTag(), item.getPos2D().getPosX(), item.getPos2D().getPosY());
        }

        //On place les npcs dans la pièce (bien évidemment il faut encore gérer l'aspect aléatoire + le facteur d'hostilité):
        Actor[] npcs = currentRoomModel.getNPCs();
        for (Actor npc : npcs) {
            int[] roomPos = currentRoomView.getRandPos();
            ActorView actorView = new ActorView("ally");
            actorView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown())
                    gameView.update(npc.getName());
            });
            currentRoomView.addInRoom(actorView, npc.getName(),
                    roomPos[0], roomPos[1]);
        }

        //On met à jour les handlers de description:
        playerView.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                gameView.update(playerModel.getName());
        });
    }
}
