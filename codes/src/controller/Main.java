package controller;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.GameView;
import view.RoomView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GameController gameController = new GameController();
        Parent root = gameController.getScene();

        primaryStage.setTitle("Silent In Space");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //On démarre la pièce au centre:
        GameView gameView = gameController.getGameView();
        RoomView currentRoomView = gameController.getCurrentRoomView();
        gameView.getMapHorizontalSlider().setValue(gameView.getMapPane().getWidth()/2 - currentRoomView.getWidth()/2);
        gameView.getMapVerticalSlider().setValue(gameView.getMapPane().getHeight()/2 - currentRoomView.getHeight()/2);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
