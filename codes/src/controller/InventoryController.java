package controller;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import model.Characters.Player;
import model.Containers.Inventory;
import model.Items.Item;
import model.Items.UsableOn;
import model.Utils.Scalar2D;
import view.ActorView;
import view.GameView;
import view.ItemView;

import java.util.LinkedHashMap;

public class InventoryController {
    //====================== ATTRIBUTS ==========================
    private final GameController gameController;
    private final GameView gameView;
    private final Player playerModel;
    private final ActorView playerView;
    private final Inventory playerInvModel;
    private final AnchorPane playerInvView;
    private final ToggleGroup invTG;
    private RoomController roomController;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public InventoryController(GameController c) {
        gameController = c;
        gameView = c.getGameView();
        playerModel = c.getPlayerModel();
        playerView = c.getPlayerView();
        playerInvModel = playerModel.getInventory();
        playerInvView = gameView.getInventoryScrollPaneAnchorPane();
        invTG = new ToggleGroup();
        initHandlers();
    }

    public void initHandlers(){
        gameView.getDropButton().setOnAction(e -> {
            String itemTag = ((ToggleButton) invTG.getSelectedToggle()).getText();
            drop(itemTag);
        });
    }

    //====================== UPDATERS =========================
    public void addInInventory(Item item){
        //On met à jour le modèle:
        playerInvModel.addItem(item);

        //On met à jour la vue:
        ToggleButton tgBtn = new ToggleButton(item.getTag());
        setTgBtnHandler(tgBtn);
        invTG.getToggles().add(tgBtn);
        playerInvView.getChildren().add(tgBtn);
        roomController.getCurrentRoomView().removeFromRoom(item.getTag());
    }

    public void drop(String itemTag){
        //On met à jour la vue:
        playerInvView.getChildren().remove((ToggleButton) invTG.getSelectedToggle());
        invTG.getToggles().remove(invTG.getSelectedToggle());
        roomController.addItemInRoom(playerInvModel.getItem(itemTag));

        //On met à jour le modèle:
        playerInvModel.removeItem(itemTag);
    }

    public void setTgBtnHandler(ToggleButton btn){
        btn.setOnAction(e -> {
            //On récupère tous les éléments visuels de la pièce associés à leurs étiquettes:
            LinkedHashMap<String, Shape> roomViews = roomController.getCurrentRoomView().getGameElementViews();

            //On parcourt chacun de ces éléments pour leur associer un gestionnaire d'événement:
            for(String viewTag : roomViews.keySet()) {
                System.out.println(viewTag);
                roomController.getCurrentRoomView().getFromRoom(viewTag).addEventHandler(MouseEvent.MOUSE_PRESSED, ev -> {
                    //Si c'est un clic gauche:
                    if(ev.isPrimaryButtonDown()) {
                        //On applique la fonction d'utilisation de l'objet définie dans le modèle:
                        roomController.getCurrentRoomModel().getInventory().getItem(btn.getText()).isUsedOn(roomController.getCurrentRoomModel().getUsableBy(viewTag));
                    }
                });
            }
        });
    }

    public void updateRoom(RoomController roomController){
        this.roomController = roomController;
    }
}
