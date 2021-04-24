package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Items.Computer;
import model.Items.File;
import model.Items.Item;
import view.ComputerView;
import view.ItemView;

import java.io.IOException;

public class ComputerController {
    //====================== ATTRIBUTS ==========================
    private ComputerView computerView;
    private final Computer computerModel;
    private final GameController gameController;
    private final VBox actorPanel;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public ComputerController(Computer computerModel, GameController gameController) throws IOException {
        //On charge la vue:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ComputerView.fxml"));
        loader.load();
        computerView = loader.getController();

        this.computerModel = computerModel;
        this.gameController = gameController;
        this.actorPanel = gameController.getGameView().getActorVBox();

        initFiles();
        initHandlers();

        //On met à jour la vue du jeu:
        updateGameView(computerView.getComputer());
    }

    private void initFiles(){
        GridPane computerDesk = computerView.getComputerDesk();
        int nbCols = computerDesk.getColumnCount();
        int colCounter = 0;
        int rowCounter = 0;

        //Pour chaque item on associe un gestionnaire d'événement:
        for(Item item : computerModel.getFILES().getItems()){
            File file = (File) item;
            ItemView itemView = new ItemView();

            itemView.setOnMousePressed(e -> {
                if(e.isSecondaryButtonDown()){
                    gameController.getGameView().update(file.getContent());
                }
                else{
                    computerModel.printFile(item.getTag(), gameController.getPlayerModel());
                    gameController.getInventoryController().updateInventory();
                }
            });

            GridPane.setHalignment(itemView, HPos.CENTER);
            GridPane.setValignment(itemView, VPos.CENTER);
            computerView.getComputerDesk().add(itemView, colCounter, rowCounter);

            //TODO: Si l'ordinateur a un nombre de fichier qui excède les places dans le GridPane?
            if(colCounter == nbCols){
                colCounter = 0;
                rowCounter++;
            }

            else{
                colCounter++;
            }
        }
    }

    private void initHandlers(){
        computerView.getQuitBtn().setOnAction(e -> {
            updateGameView(actorPanel);
        });

        computerView.getEventBtn().setOnAction(e -> {
            computerModel.getEVENT().getE().raise(gameController.getPlayerModel());
        });
    }

    //====================== UPDATERS =========================
    public void updateGameView(VBox replacer){
        VBox currentActorPanel = gameController.getGameView().getActorVBox();
        gameController.getGameView().getStoryBox().getChildren().remove(currentActorPanel);
        gameController.getGameView().setActorVBox(replacer);
        gameController.getGameView().getStoryBox().getChildren().add(0, replacer);
    }
}
