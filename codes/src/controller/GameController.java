package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import model.Game.SIS;
import model.Location.Room;
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

        initTestRoom();
    }

    public void initTestRoom()
    {
        Room playerRoom = gameModel.getShip().getPlayer().getRoom();
        GridPane roomView = mainPanelController.getMapPanelController().getRoomView();

        Circle playerView = new Circle();
        playerView.setRadius(10);
        playerView.setFill(Color.BLUE);
        GridPane.setMargin(playerView, new Insets(5, 5, 5, 5));
        roomView.add(playerView, 2, 2);
    }

    public HBox getScene() {
        return mainPanelController.getScene();
    }
}
