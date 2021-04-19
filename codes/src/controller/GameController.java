package controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import model.Characters.NPC;
import model.Characters.Player;
import model.Doors.Door;
import model.Doors.LockedDoor;
import model.Game.SIS;
import model.Items.Item;
import model.Location.Room;
import view.*;

import java.io.IOException;
import java.util.Set;

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

        updateRoomView(11, 11);
        initHandlers();
    }

    public void initHandlers() {
        //Pour que la pièce passe derrière la fenêtre si débordement:
        final Rectangle clipPane = new Rectangle();
        gameView.getMapPane().setClip(clipPane);
        gameView.getMapPane().layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipPane.setWidth(newValue.getWidth());
            clipPane.setHeight(newValue.getHeight());
        });

        gameView.getZoomPlusButton().setOnAction(e -> {
            currentRoomView.setScaleX(currentRoomView.getScaleX() * 1.1);
            currentRoomView.setScaleY(currentRoomView.getScaleY() * 1.1);
        });

        gameView.getZoomMinusButton().setOnAction(e -> {
            currentRoomView.setScaleX(currentRoomView.getScaleX() * 10.0/11.0);
            currentRoomView.setScaleY(currentRoomView.getScaleY() * 10.0/11.0);
        });

        gameView.getMapHorizontalSlider().maxProperty().bind(
                Bindings.subtract(gameView.getMapPane().widthProperty(), currentRoomView.widthProperty()));
        gameView.getMapVerticalSlider().maxProperty().bind(
                Bindings.subtract(gameView.getMapPane().heightProperty(), currentRoomView.heightProperty()));

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

    //====================== GETTERS ==========================
    public RoomView getCurrentRoomView() {
        return currentRoomView;
    }

    public GameView getGameView() {
        return gameView;
    }
    public ActorView getNPCView(NPC npc) {
        if(npc.isHostile())
            return new ActorView("hostile");
        else if (npc.isAlly())
            return new ActorView("ally");
        else
            return new ActorView("neutral");
    }
    public HBox getScene() {
        return gameView.getScene();
    }


    //====================== UPDATERS =========================
    public void updateRoomView(int nbCol, int nbLignes) {
        currentRoomView = new RoomView(nbCol, nbLignes);
        currentRoomView.layoutXProperty().bind(gameView.getMapHorizontalSlider().valueProperty());
        currentRoomView.layoutYProperty().bind(gameView.getMapVerticalSlider().valueProperty());

        gameView.getRoomLabel().setText("Room " + currentRoomModel.getID());
        loadDoors();
        loadItems();
        loadPlayer();
        loadNPCs();
        gameView.getMapPane().getChildren().add(currentRoomView);
    }


    //====================== LOADERS ==========================
    public void loadDoors() {
        Set<Door> doors = currentRoomModel.getDoors().keySet();
        int[] roomSize = {currentRoomView.getNbCol(), currentRoomView.getNbLignes()};

        for(Door d : doors) {
            DoorView doorView;
            int[] doorPos = {d.getPos2D().getPosX(), d.getPos2D().getPosY()};
            if(d instanceof LockedDoor) {
                doorView = new DoorView("locked");
                currentRoomView.addInRoom(doorView, d.getTag(), doorPos[0], doorPos[1], doorView.getAlignment(roomSize, doorPos));
            }
            else {
                doorView = new DoorView("normal");
                currentRoomView.addInRoom(doorView, d.getTag(), doorPos[0], doorPos[1], doorView.getAlignment(roomSize, doorPos));
            }
            doorView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown())
                    d.describe();
            });
        }
    }

    public void loadItems() {
        Item[] items = currentRoomModel.getInventory().getItems();
        for (Item item : items) {
            if(item.isTakable()) {
                ItemView itemView = new ItemView();
                itemView.setOnMousePressed(e -> {
                    if(e.isSecondaryButtonDown())
                        gameView.update(item.getDescription());
                });
                currentRoomView.addInRoom(itemView, item.getTag(),
                        item.getPos2D().getPosX(), item.getPos2D().getPosY(), "CENTER");
            }
            else {
                ContainerView containerView = new ContainerView("HealthStation");
                containerView.setOnMousePressed(e -> {
                    if(e.isSecondaryButtonDown())
                        gameView.update(item.getDescription());
                });
                currentRoomView.addInRoom(containerView, item.getTag(),
                        item.getPos2D().getPosX(), item.getPos2D().getPosY(), "CENTER");
            }
        }
    }

    public void loadNPCs() {
        NPC[] npcs = currentRoomModel.getNPCs();
        for (NPC npc : npcs) {
            int[] roomPos = currentRoomView.getRandPos();
            ActorView actorView = getNPCView(npc);
            actorView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown())
                    gameView.update(npc.getName());
            });
            currentRoomView.addInRoom(actorView, npc.getName(), roomPos[0], roomPos[1], "CENTER");
        }
    }

    public void loadPlayer() {
        int nbCol = currentRoomView.getNbCol();
        int nbLignes = currentRoomView.getNbLignes();
        currentRoomView.addInRoom(playerView, playerModel.getName(),
                (nbCol - 1)/2, (nbLignes-1)/2, "CENTER");
    }
}
