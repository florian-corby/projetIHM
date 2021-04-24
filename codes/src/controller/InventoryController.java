package controller;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import model.Characters.Player;
import model.Containers.Inventory;
import model.Items.Item;
import view.GameView;

import java.util.LinkedHashMap;


/* -----------------------------------------------------------------------------
 * Contrôleur de l'inventaire du jeu:
 *
 * Rôle: Contrôleur global du jeu qui gère l'inventaire du joueur et son UI.
 * ----------------------------------------------------------------------------- */

public class InventoryController {
    private final GameController gameController;
    private final Inventory playerInvModel;
    private final VBox playerInvView;
    private final ToggleGroup invTG;
    private EventHandler<MouseEvent>[] fireHandlers;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public InventoryController(GameController c) {
        gameController = c;
        Player playerModel = c.getPlayerModel();
        playerInvModel = playerModel.getInventory();
        playerInvView = c.getGameView().getInventoryVBox();
        invTG = new ToggleGroup();
        initHandlers();
        initInventory();
    }

    public void initHandlers(){
        //On initialise le handler du bouton drop():
        gameController.getGameView().getDropButton().setOnAction(e -> {
            String itemTag = ((ToggleButton) invTG.getSelectedToggle()).getText();
            drop(itemTag);
        });

        gameController.getGameView().getGiveButton().setOnAction(e -> { give(); });
        gameController.getGameView().getLookButton().setOnAction(e -> { look(); });
    }

    public void initInventory(){
        for(Item item : playerInvModel.getItems()){
            ToggleButton tgBtn = new ToggleButton(item.getTag());
            setTgBtnHandler(tgBtn);
            invTG.getToggles().add(tgBtn);
            playerInvView.getChildren().add(tgBtn);
        }
    }

    //====================== UPDATERS =========================
    public void addInInventory(Item item){
        //On met à jour le modèle:
        playerInvModel.addItem(item);
        gameController.getRoomController().getCurrentRoomModel().getInventory().removeItem(item.getTag());

        //On met à jour la vue:
        ToggleButton tgBtn = new ToggleButton(item.getTag());
        setTgBtnHandler(tgBtn);
        invTG.getToggles().add(tgBtn);
        playerInvView.getChildren().add(tgBtn);

        if(gameController.getRoomController().getCurrentRoomView().getFromRoom(item.getTag()) != null)
            gameController.getRoomController().getCurrentRoomView().removeFromRoom(item.getTag());
    }

    public void clearEventHandlers(){
        LinkedHashMap<String, Shape> roomViews = gameController.getRoomController().getCurrentRoomView().getGameElementViews();
        int count = 0;
        for (String viewTag : roomViews.keySet()) {
            if(fireHandlers[count] != null) {
                gameController.getRoomController().getCurrentRoomView().getFromRoom(viewTag).removeEventHandler(MouseEvent.MOUSE_PRESSED, fireHandlers[count]);
                count++;
            }
        }
    }

    public void drop(String itemTag){
        //On met à jour la vue:
        playerInvView.getChildren().remove((ToggleButton) invTG.getSelectedToggle());
        invTG.getToggles().remove(invTG.getSelectedToggle());
        int[] pos = gameController.getRoomController().getCurrentRoomView().getRandPos();
        gameController.getRoomController().addItemInRoom(playerInvModel.getItem(itemTag), pos[0], pos[1]);

        //On met à jour le modèle:
        playerInvModel.moveItem(itemTag, gameController.getRoomController().getCurrentRoomModel().getInventory());

        //On élimine les handlers() dus à la sélection du bouton:
        clearEventHandlers();
    }

    public void give(){
        ToggleButton itemBtn = (ToggleButton) invTG.getSelectedToggle();

        if(itemBtn != null){
            String actorTag = gameController.getGameView().getActorLabel().getText();
            String itemTag = itemBtn.getText();

            //On met à jour le modèle:
            gameController.getPlayerModel().give(itemTag, gameController.getRoomController().getCurrentRoomModel().getActor(actorTag));

            //On met à jour la vue:
            updateInventory();
        }
    }

    public void look(){
        ToggleButton itemBtn = (ToggleButton) invTG.getSelectedToggle();

        if(itemBtn != null){
            String itemTag = itemBtn.getText();
            playerInvModel.getItem(itemTag).describe();
        }
    }

    public void setTgBtnHandler(ToggleButton btn){
        btn.setOnAction(e -> {
            //On fait le ménage dans le tableau des handlers (si un objet de l'inventaire avait été sélectionné avant par exemple...) :
            clearEventHandlers();

            //On récupère tous les éléments visuels de la pièce associés à leurs étiquettes:
            LinkedHashMap<String, Shape> roomViews = gameController.getRoomController().getCurrentRoomView().getGameElementViews();

            //On récupère l'élément du modèle qui devra appeler la fonction du modèle si cet élément n'est pas
            //dans l'inventaire de la pièce c'est qu'il est dans l'inventaire du joueur:
            Item itemUsed;
            if(gameController.getRoomController().getCurrentRoomModel().getInventory().getItem(btn.getText()) != null)
                itemUsed = gameController.getRoomController().getCurrentRoomModel().getInventory().getItem(btn.getText());
            else
                itemUsed = playerInvModel.getItem(btn.getText());

            //On parcourt chacun de ces éléments pour leur associer un gestionnaire d'événement:
            int count = 0;
            for(String viewTag : roomViews.keySet()) {
                EventHandler<MouseEvent> useOnHandler = ev -> {
                    if (ev.isPrimaryButtonDown()) {
                        //On applique la fonction d'utilisation de l'objet définie dans le modèle:
                        itemUsed.isUsedOn(gameController.getRoomController().getCurrentRoomModel().getUsableBy(viewTag));
                        clearEventHandlers();
                        btn.setSelected(false);
                    }
                };
                fireHandlers[count++] = useOnHandler;
                gameController.getRoomController().getCurrentRoomView().getFromRoom(viewTag).addEventHandler(MouseEvent.MOUSE_PRESSED, useOnHandler);
            }
        });
    }

    public void updateInventory(){
        int nbToggleBtns = invTG.getToggles().size();

        for(int i = 0; i < nbToggleBtns; i++){
            //À chaque suppression les Toggles se réarrangent. On supprime donc le premier toggle "n" fois:
            ToggleButton itemBtn = (ToggleButton) invTG.getToggles().get(0);
            playerInvView.getChildren().remove(itemBtn);
            invTG.getToggles().remove(itemBtn);
        }

        clearEventHandlers();
        initInventory();
    }

    public void updateRoom(RoomController roomController){
        LinkedHashMap<String, Shape> roomViews = roomController.getCurrentRoomView().getGameElementViews();

        //On va stocker tous les gestionnaires d'événements que la sélection d'un bouton aura créé dans un tableau:
        fireHandlers = new EventHandler[roomViews.size()];
    }
}
