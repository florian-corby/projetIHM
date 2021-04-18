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

        playerView.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                gameView.update(playerModel.getName());
        });
    }

    public void initTestRoom()
    {
        updateRoomView(11, 11);

        //Ajout de la porte T:
        DoorView doorView = new DoorView("locked", HPos.RIGHT);
        currentRoomView.addInRoom(doorView, currentRoomModel.getDoor(0).getTag(), 10, 5);
    }

    //====================== GETTERS ==========================
    public HBox getScene() {
        return gameView.getScene();
    }


    //====================== UPDATERS =========================
    public void updateRoomView(int nbCol, int nbLignes)
    {
        currentRoomView = new RoomView(nbCol, nbLignes);
        gameView.getRoomLabel().setText("Room " + currentRoomModel.getID());

        loadItems();
        loadPlayer();
        loadNPCs();

        gameView.getMapHBox().getChildren().add(currentRoomView);
    }

    //====================== LOADERS ==========================
    public void loadItems()
    {
        Item[] items = currentRoomModel.getInventory().getItems();
        for (Item item : items) {
            if(item.isTakable())
            {
                ItemView itemView = new ItemView();
                itemView.setOnMousePressed(e -> {
                    if(e.isSecondaryButtonDown())
                        gameView.update(item.getDescription());
                });
                currentRoomView.addInRoom(itemView, item.getTag(), item.getPos2D().getPosX(), item.getPos2D().getPosY());
            }

            else
            {
                ContainerView containerView = new ContainerView("HealthStation");
                containerView.setOnMousePressed(e -> {
                    if(e.isSecondaryButtonDown())
                        gameView.update(item.getDescription());
                });
                currentRoomView.addInRoom(containerView, item.getTag(), item.getPos2D().getPosX(), item.getPos2D().getPosY());
            }
        }
    }

    public void loadPlayer(){
        int nbCol = currentRoomView.getNbCol();
        int nbLignes = currentRoomView.getNbLignes();
        currentRoomView.addInRoom(playerView, playerModel.getName(), (nbCol - 1)/2, (nbLignes-1)/2);
    }

    public void loadNPCs()
    {
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
    }
}
