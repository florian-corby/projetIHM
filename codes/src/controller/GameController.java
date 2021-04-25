package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import model.Characters.Player;
import model.Game.SIS;
import model.Utils.Scalar2D;
import view.*;
import java.io.IOException;


/* -----------------------------------------------------------------------------
 * Contrôleur du jeu:
 *
 * Rôle: Lance le jeu par le biais de plusieurs modèles MVC chacun spécialisé
 * sur une partie du jeu (Exemple: pour les pièces du jeu => le RoomController,
 * pour les acteurs => le ActorController, etc.) Il initialise les gestionnaires
 * d'événements globaux du jeu (menu d'aide, load()/save(), etc.) et fournit les
 * méthodes principale du jeu comme la réaction à une fin de partie.
 * ----------------------------------------------------------------------------- */

public class GameController {
    public final static Scalar2D DEFAULT_ROOMS_SIZE = new Scalar2D(11, 11);

    //Quelques éléments du modèle et leurs vues associées:
    private final SIS gameModel;
    private final GameView gameView;
    private Player playerModel;
    private final ActorView playerView = new ActorView("player");

    //Les sous-contrôleurs spécialisés:
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
        gameView.setMapPaneClip();
        gameView.setCenteredSlidersOnWindowRedimensionned();

        //On charge le modèle:
        gameModel = new SIS(gameView);
        playerModel = gameModel.getShip().getPlayer();

        //On initialise les sous-contrôleurs spécialisés qui par incidence lancent le jeu:
        actorController = new ActorController(this);
        inventoryController = new InventoryController(this);
        roomController = new RoomController(this);

        //On charge les gestionnaires d'événement globaux du jeu:
        initSaveLoadHandlers();
        initAttackHandler();
        initHelpManual();
    }

    //Le manuel d'aide:
    public void initHelpManual() {
        previousDialog = gameView.getDialogTextArea().getText();
        gameView.getHelpButton().setOnAction(e -> {
            if (isHelpManualOn) {
                isHelpManualOn = false;
                gameView.getHelpButton().setText("?");
                gameView.getDialogTextArea().setText(previousDialog);
            } else {
                isHelpManualOn = true;
                previousDialog = gameView.getDialogTextArea().getText();
                gameView.getDialogTextArea().setText("");
                gameView.getHelpButton().setText("Back to the Game");
                gameModel.printHelp();
            }
        });
    }

    // Gestionnaire des sauvgardes
    public void initSaveLoadHandlers(){
        gameView.getSaveButton().setOnMouseClicked(e -> playerModel.save());

        gameView.getLoadButton().setOnMouseClicked(e-> {
            gameModel.load();
            playerModel = gameModel.getShip().getPlayer();
            roomController.updateRoomView(DEFAULT_ROOMS_SIZE.getScalar2DCol(), DEFAULT_ROOMS_SIZE.getScalar2DLine());
            inventoryController.updateInventory();
        });
    }

    // Gestionnaire des attaques
    public void initAttackHandler(){ gameView.getAttackButton().setOnMouseClicked(e-> { actorController.attack(); }); }

    //====================== PREDICATS ==========================

    // Gestion de la fin du jeu
    public void isGameOver(){
        if(gameModel.isEndGame()){
            getActorController().getMoveNPCsService().cancel();
            Alert popup = new Alert(Alert.AlertType.INFORMATION);
            popup.setTitle("Félicitations!");
            popup.setContentText("Merci d'avoir joué à Silent In Space! Et tout particulièrement merci à notre bêta-testeuse Ophélie De Sousa Oliveira :) !");
            popup.showAndWait();
            Platform.exit();
        }
    }

    //====================== GETTERS ==========================
    public ActorController getActorController() { return actorController; }
    public InventoryController getInventoryController() { return inventoryController; }
    public RoomController getRoomController() { return roomController; }
    public SIS getGameModel(){ return gameModel; }
    public GameView getGameView() { return gameView; }
    public Player getPlayerModel() { return playerModel; }
    public ActorView getPlayerView() { return playerView; }
    public HBox getScene() { return gameView.getSceneHBox(); }
}
