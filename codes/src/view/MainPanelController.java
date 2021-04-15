package view;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import model.Game.MessageListener;


public class MainPanelController implements MessageListener
{
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

    public HBox getScene()
    {
        return sceneHBox;
    }

    @Override
    public void update(String message) {
        dialogPanelController.updateMessage(message);
    }
}
