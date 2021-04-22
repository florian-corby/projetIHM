package controller;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.VBox;
import model.Characters.NPC;
import model.Characters.Player;
import model.Doors.Door;
import model.Doors.LockedDoor;
import model.Items.Computer;
import model.Items.HealthStation;
import model.Items.Item;
import model.Location.Room;
import view.*;

import java.io.IOException;
import java.util.Set;

import static controller.GameController.DEFAULT_ROOMS_SIZE;

public class RoomController {
    private final Player playerModel;
    private final ActorView playerView;
    private final GameController gameController;
    private final GameView gameView;
    private final InventoryController playerInvController;
    private Room currentRoomModel;
    private RoomView currentRoomView;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public RoomController(GameController c) {
        gameController = c;
        gameView = c.getGameView();
        playerModel = c.getPlayerModel();
        playerView = c.getPlayerView();
        playerInvController = c.getInventoryController();

        //On charge la première pièce:
        this.updateRoomView(DEFAULT_ROOMS_SIZE.getScalar2DCol(), DEFAULT_ROOMS_SIZE.getScalar2DLine());
        gameController.getInventoryController().updateRoom(this);
        gameController.getInventoryController().initInventory();
    }

    //====================== GETTERS ==========================
    public Room getCurrentRoomModel() { return currentRoomModel; }
    public RoomView getCurrentRoomView() { return currentRoomView; }


    //====================== UPDATERS =========================
    public void addContainerInRoom(Item item){
        ContainerView containerView = new ContainerView("HealthStation");
        containerView.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown())
                item.describe();
            else {
                if (item instanceof HealthStation)
                    item.isUsedOn(playerModel);
                else if(item instanceof Computer){
                    Computer computer = (Computer) item;
                    try {
                        new ComputerController(computer, gameController);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                else
                    item.isUsed(playerModel);
            }
        });
        currentRoomView.addInRoom(containerView, item.getTag(),
                item.getScalar2D().getScalar2DCol(), item.getScalar2D().getScalar2DLine(), "CENTER");
    }
    public void addItemInRoom(Item item){
        ItemView itemView = new ItemView();

        itemView.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown())
                item.describe();
            else
                playerInvController.addInInventory(item);
        });

        currentRoomView.addInRoom(itemView, item.getTag(),
                item.getScalar2D().getScalar2DCol(), item.getScalar2D().getScalar2DLine(), "CENTER");
    }


    public void updateRoomModel(){ currentRoomModel = playerModel.getRoom(); }
    public void updateRoomView(int nbCol, int nbLignes) {
        gameController.isGameOver();
        gameView.getMapPane().getChildren().remove(currentRoomView);
        updateRoomModel();
        currentRoomView = new RoomView(nbCol, nbLignes);
        gameView.getRoomLabel().setText("Room " + currentRoomModel.getID());
        loadDoors();
        loadItems();
        loadPlayer();
        loadNPCs();
        loadHandlers();
        gameController.getActorController().resetActorPanel();
        gameView.getMapPane().getChildren().add(currentRoomView);
    }


    //====================== LOADERS ==========================
    public void loadDoors() {
        Set<Door> doors = currentRoomModel.getDoors().keySet();
        int[] roomSize = {currentRoomView.getNbCol(), currentRoomView.getNbLignes()};

        for(Door d : doors) {
            DoorView doorView;
            int[] doorPos = {d.getScalar2D().getScalar2DCol(), d.getScalar2D().getScalar2DLine()};

            if(d instanceof LockedDoor && ((LockedDoor) d).isLocked())
                doorView = new DoorView("locked");
            else if(d instanceof LockedDoor && !((LockedDoor) d).isLocked())
                doorView = new DoorView("normal");
            else
                doorView = new DoorView("normal");

            doorView.setDoorGeometry(roomSize, doorPos);
            currentRoomView.addInRoom(doorView, d.getTag(), doorPos[0], doorPos[1], doorView.getAlignment(roomSize, doorPos));

            doorView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown())
                    d.describe();
                else{
                    playerModel.go(d);
                    updateRoomView(DEFAULT_ROOMS_SIZE.getScalar2DCol(), DEFAULT_ROOMS_SIZE.getScalar2DLine());
                }
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
            if(item.isTakable())
                addItemInRoom(item);
            else
                addContainerInRoom(item);
        }
    }

    public void loadNPCs() {
        NPC[] npcs = currentRoomModel.getNPCs();

        if(npcs == null)
            return;

        for (NPC npc : npcs) {
            int[] roomPos = currentRoomView.getRandPos();
            ActorView actorView = ActorView.getNPCView(npc);
            actorView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown())
                    gameView.update(npc.getName());
                else{
                    gameController.getActorController().updateNPCFrame(npc);
                    npc.talk();
                }
            });
            currentRoomView.addInRoom(actorView, npc.getName(), roomPos[0], roomPos[1], "CENTER");
        }
    }

    public void loadPlayer() {
        int nbCol = currentRoomView.getNbCol();
        int nbLignes = currentRoomView.getNbLignes();

        gameController.getActorController().updatePlayerFrame();
        currentRoomView.addInRoom(playerView, playerModel.getName(),
                (nbCol - 1)/2, (nbLignes-1)/2, "CENTER");
        
        playerView.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                gameView.update(playerModel.getName());
            else
                gameController.getActorController().updatePlayerFrame();
        });
    }
}
