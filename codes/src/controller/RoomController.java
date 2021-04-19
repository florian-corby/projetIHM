package controller;

import javafx.beans.binding.Bindings;
import model.Characters.NPC;
import model.Characters.Player;
import model.Doors.Door;
import model.Doors.LockedDoor;
import model.Items.Item;
import model.Location.Room;
import view.*;

import java.util.Set;

public class RoomController {
    private final GameController gameController;
    private final GameView gameView;
    private final Player playerModel;
    private final ActorView playerView;
    private Room currentRoomModel;
    private RoomView currentRoomView;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public RoomController(GameController c)
    {
        gameController = c;
        gameView = c.getGameView();
        currentRoomModel = c.getCurrentRoomModel();
        playerModel = c.getPlayerModel();
        playerView = c.getPlayerView();
    }

    //====================== UPDATERS =========================
    public void updateRoomView(int nbCol, int nbLignes) {
        currentRoomModel = gameController.getCurrentRoomModel();
        currentRoomView = new RoomView(nbCol, nbLignes);
        gameView.getRoomLabel().setText("Room " + currentRoomModel.getID());
        loadDoors();
        loadItems();
        loadPlayer();
        loadNPCs();
        loadHandlers();
        gameView.getMapPane().getChildren().add(currentRoomView);
    }


    //====================== LOADERS ==========================
    public void loadDoors() {
        Set<Door> doors = currentRoomModel.getDoors().keySet();
        int[] roomSize = {currentRoomView.getNbCol(), currentRoomView.getNbLignes()};

        for(Door d : doors) {
            DoorView doorView;
            int[] doorPos = {d.getScalar2D().getScalar2DCol(), d.getScalar2D().getScalar2DLine()};
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

    public void loadHandlers() {
        currentRoomView.layoutXProperty().bind(gameView.getMapHorizontalSlider().valueProperty());
        currentRoomView.layoutYProperty().bind(gameView.getMapVerticalSlider().valueProperty());

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
                        item.getScalar2D().getScalar2DCol(), item.getScalar2D().getScalar2DLine(), "CENTER");
            }
            else {
                ContainerView containerView = new ContainerView("HealthStation");
                containerView.setOnMousePressed(e -> {
                    if(e.isSecondaryButtonDown())
                        gameView.update(item.getDescription());
                });
                currentRoomView.addInRoom(containerView, item.getTag(),
                        item.getScalar2D().getScalar2DCol(), item.getScalar2D().getScalar2DLine(), "CENTER");
            }
        }
    }

    public void loadNPCs() {
        NPC[] npcs = currentRoomModel.getNPCs();
        for (NPC npc : npcs) {
            int[] roomPos = currentRoomView.getRandPos();
            ActorView actorView = gameController.getNPCView(npc);
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
