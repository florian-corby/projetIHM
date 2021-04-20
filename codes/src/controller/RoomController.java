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
    private final InventoryController playerInvController;
    private Room currentRoomModel;
    private RoomView currentRoomView;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public RoomController(GameController c)
    {
        gameController = c;
        gameView = c.getGameView();
        playerModel = c.getPlayerModel();
        playerView = c.getPlayerView();
        playerInvController = c.getInventoryController();
    }

    //====================== GETTERS ==========================
    public Room getCurrentRoomModel() { return currentRoomModel; }
    public RoomView getCurrentRoomView() { return currentRoomView; }

    //====================== UPDATERS =========================
    public void addContainerInRoom(Item item){
        ContainerView containerView = new ContainerView("HealthStation");
        containerView.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                item.describe();
            else
                item.isUsedOn(playerModel);
        });
        currentRoomView.addInRoom(containerView, item.getTag(),
                item.getScalar2D().getScalar2DCol(), item.getScalar2D().getScalar2DLine(), "CENTER");
    }
    public void addItemInRoom(Item item){
        ItemView itemView = new ItemView();
        itemView.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                item.describe();
            else
                playerInvController.addInInventory(item);
        });
        currentRoomView.addInRoom(itemView, item.getTag(),
                item.getScalar2D().getScalar2DCol(), item.getScalar2D().getScalar2DLine(), "CENTER");
    }

    public void updateNPCFrame(NPC npc){
        ActorView actorView = gameController.getNPCView(npc);
        String colorString = "#"+actorView.getFill().toString().substring(2);
        gameView.getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorLabel().setText(npc.getName());
    }

    public void updatePlayerFrame(){
        //0xffab8d devient #ffab8d car c'est c'est le format que comprend javafx:
        String colorString = "#"+playerView.getFill().toString().substring(2);
        gameView.getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorLabel().setText(playerModel.getName());
    }

    public void updateRoomModel(){ currentRoomModel = playerModel.getRoom(); }
    public void updateRoomView(int nbCol, int nbLignes) {
        gameView.getMapPane().getChildren().remove(currentRoomView);
        updateRoomModel();
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
                    updateRoomView(GameController.DEFAULT_ROOMS_SIZE.getScalar2DCol(), GameController.DEFAULT_ROOMS_SIZE.getScalar2DLine());
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
            ActorView actorView = gameController.getNPCView(npc);
            actorView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown())
                    gameView.update(npc.getName());
                else{
                    updateNPCFrame(npc);
                    npc.talk();
                }
            });
            currentRoomView.addInRoom(actorView, npc.getName(), roomPos[0], roomPos[1], "CENTER");
        }
    }

    public void loadPlayer() {
        int nbCol = currentRoomView.getNbCol();
        int nbLignes = currentRoomView.getNbLignes();

        updatePlayerFrame();
        currentRoomView.addInRoom(playerView, playerModel.getName(),
                (nbCol - 1)/2, (nbLignes-1)/2, "CENTER");
        
        playerView.setOnMousePressed(e -> {
            if(e.isSecondaryButtonDown())
                gameView.update(playerModel.getName());
            else
                updatePlayerFrame();
        });
    }
}
