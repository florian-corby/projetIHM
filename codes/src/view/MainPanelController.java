package view;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.Game.MessageListener;


public class MainPanelController implements MessageListener
{
    //====================== ATTRIBUTS ==========================
    @FXML
    private ActorPanelController actorPanelController;

    @FXML
    private DialogPanelController dialogPanelController;

    @FXML
    private GamePanelController gamePanelController;

    @FXML
    private InventoryPanelController inventoryPanelController;

    @FXML
    private HBox sceneHBox;

    @FXML
    private MapPanelController mapPanelController;

    //====================== GETTERS ==========================
    public HBox getScene()
    {
        return sceneHBox;
    }

    public ActorPanelController getActorPanelController() {
        return actorPanelController;
    }

    public DialogPanelController getDialogPanelController() {
        return dialogPanelController;
    }

    public GamePanelController getGamePanelController() {
        return gamePanelController;
    }

    public InventoryPanelController getInventoryPanelController() {
        return inventoryPanelController;
    }

    public MapPanelController getMapPanelController() {
        return mapPanelController;
    }


    //====================== AUTRES ==========================
    @Override
    public void update(String message) {
        dialogPanelController.updateMessage(message);
    }
}
