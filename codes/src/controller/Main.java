package controller;

import controller.Game.SISController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SIS_sceneV2.fxml"));
        Parent root = loader.load();
        SisSceneController controller = loader.getController();
        controller.setGame(new SISController(controller.getDialogTextArea()));

        primaryStage.setTitle("Silent In Space");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
