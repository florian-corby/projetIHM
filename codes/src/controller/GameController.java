package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import model.Characters.NPC;
import model.Characters.Player;
import model.Containers.Inventory;
import model.Game.SIS;
import model.Location.Room;
import model.Utils.Scalar2D;
import view.*;
import java.io.IOException;

public class GameController {
    //====================== ATTRIBUTS ==========================
    private final Scalar2D DEFAULT_ROOMS_SIZE = new Scalar2D(11, 11);

    //Quelques éléments du modèle:
    private final SIS gameModel;
    private Room currentRoomModel;
    private final Player playerModel;

    //Quelques éléments de la vue (pour manipuler une pièce on passe par son contrôleur):
    private final GameView gameView;
    private final RoomController roomController;
    private final InventoryController inventoryController;
    private final ActorView playerView = new ActorView("player");

    //Gestion du manuel d'aide:
    private String previousDialog;
    private Boolean isHelpManualOn = false;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public GameController() throws IOException {
        //On charge la vue:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameView.fxml"));
        loader.load();
        gameView = loader.getController();

        //On charge le modèle et le gestionnaire d'inventaire:
        gameModel = new SIS(gameView);
        playerModel = gameModel.getShip().getPlayer();
        currentRoomModel = playerModel.getRoom();
        inventoryController = new InventoryController(this);

        //On charge la pièce:
        roomController = new RoomController(this);
        roomController.updateRoomView(DEFAULT_ROOMS_SIZE.getScalar2DCol(), DEFAULT_ROOMS_SIZE.getScalar2DLine());
        inventoryController.updateRoom(roomController);

        //On charge les gestionnaires d'événement globaux du jeu:
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

        //Les sliders réinitialisent la pièce au centre du panneau à chaque redimensionnement de la fenêtre:
        gameView.getMapHorizontalSlider().maxProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                gameView.getMapHorizontalSlider().setValue(gameView.getMapHorizontalSlider().getMax()/2);
            }
        });
        gameView.getMapVerticalSlider().maxProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                gameView.getMapVerticalSlider().setValue(gameView.getMapVerticalSlider().getMax()/2);
            }
        });

        //Le manuel d'aide:
        previousDialog = gameView.getDialogTextArea().getText();
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

    //====================== GETTERS ==========================
    public Room getCurrentRoomModel() { return currentRoomModel; }
    public InventoryController getInventoryController() { return inventoryController; }
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
    public Player getPlayerModel() { return playerModel; }
    public ActorView getPlayerView() { return playerView; }
    public RoomController getRoomController() { return roomController; }
    public HBox getScene() {
        return gameView.getScene();
    }
}
