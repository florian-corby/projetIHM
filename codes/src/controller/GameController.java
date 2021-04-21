package controller;

import javafx.fxml.FXMLLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.Characters.Player;
import model.Game.SIS;
import model.Utils.Scalar2D;
import view.*;
import java.io.IOException;

public class GameController {
    //====================== ATTRIBUTS ==========================
    public final static Scalar2D DEFAULT_ROOMS_SIZE = new Scalar2D(11, 11);

    //Quelques éléments du modèle et leurs vues associées:
    private final SIS gameModel;
    private final GameView gameView;
    private final Player playerModel;
    private final ActorView playerView = new ActorView("player");

    //Quelques éléments de la vue (pour manipuler une pièce on passe par son contrôleur):
    private final ActorController actorController;
    private final RoomController roomController;
    private final InventoryController inventoryController;

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
        actorController = new ActorController(this);
        inventoryController = new InventoryController(this);

        //On charge la pièce:
        roomController = new RoomController(this);
        roomController.updateRoomView(DEFAULT_ROOMS_SIZE.getScalar2DCol(), DEFAULT_ROOMS_SIZE.getScalar2DLine());
        inventoryController.updateRoom(roomController);
        inventoryController.initInventory();

        //On charge les gestionnaires d'événement globaux du jeu:
        initHandlers();
    }

    public void initHandlers()
    {
/*
        // ========== ALIEN
        currentRoomView.getFromRoom("Alien").setOnMousePressed(e -> {
            if(e.isPrimaryButtonDown())
            gameView.getActorImageView().setImage(new Image("../../libraries/img/alien"));
                });
*/

        // ========== SAVE & LOAD BUTTON
        gameView.getSaveButton().setOnMouseClicked(e-> {
            playerModel.save();
            gameView.update("You successfully saved the game!");
        });

        gameView.getLoadButton().setOnMouseClicked(e-> {
            playerModel.load();
            gameView.update("You successfully loaded the game!");
        });

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
                gameView.getDialogTextArea().setText("");
                gameView.getHelpButton().setText("Back to the Game");
                gameModel.printHelp();
            }
        });
    }

    //====================== GETTERS ==========================
    public ActorController getActorController() { return actorController; }
    public InventoryController getInventoryController() { return inventoryController; }
    public SIS getGameModel(){ return gameModel; }
    public GameView getGameView() { return gameView; }
    public Player getPlayerModel() { return playerModel; }
    public ActorView getPlayerView() { return playerView; }
    public RoomController getRoomController() { return roomController; }
    public HBox getScene() { return gameView.getSceneHBox(); }
}
