package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import model.Game.SIS;
import view.MainPanelController;

import java.io.IOException;

public class GameController {

    private final MainPanelController mainPanelController;
    private final SIS gameModel;

    public GameController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainPanel.fxml"));
        loader.load();
        mainPanelController = loader.getController();
        gameModel = new SIS(mainPanelController);
    }

    public HBox getScene() {
        return mainPanelController.getScene();
    }
}
