package controller;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import model.Characters.Player;
import model.Containers.Inventory;
import model.Items.Item;
import view.ActorView;
import view.GameView;

public class InventoryController {
    //====================== ATTRIBUTS ==========================
    private final GameController gameController;
    private final GameView gameView;
    private final Player playerModel;
    private final ActorView playerView;
    private final Inventory playerInvModel;
    private final AnchorPane playerInvView;
    private RoomController roomController;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public InventoryController(GameController c) {
        gameController = c;
        gameView = c.getGameView();
        playerModel = c.getPlayerModel();
        playerView = c.getPlayerView();
        playerInvModel = playerModel.getInventory();
        playerInvView = gameView.getInventoryScrollPaneAnchorPane();
    }

    //====================== GETTERS ==========================

    //====================== UPDATERS =========================
    public void addInInventory(Item item){
        playerInvView.getChildren().add(new ToggleButton(item.getTag()));
        roomController.getCurrentRoomView().removeFromRoom(item.getTag());
    }

    public void updateRoom(RoomController roomController){
        this.roomController = roomController;
    }
}
