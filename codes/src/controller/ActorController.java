package controller;

import javafx.scene.paint.Color;
import model.Characters.Actor;
import javafx.scene.layout.VBox;
import model.Characters.NPC;
import model.Utils.Scalar2D;
import view.ActorView;

import static controller.GameController.DEFAULT_ROOMS_SIZE;

/* -----------------------------------------------------------------------------
 * Contrôleur des acteurs du jeu:
 *
 * Rôle: Contrôleur global du jeu qui gère les acteurs (les npcs et le joueur) du
 * jeu ainsi que le panneau qui leur est dédié dans la vue du jeu
 * ----------------------------------------------------------------------------- */

public class ActorController {
    private final GameController gameController;

    //Gestion de l'ordinateur:
    private final VBox actorPanel;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public ActorController(GameController c) {
        gameController = c;
        actorPanel = c.getGameView().getActorVBox();
    }

    //====================== GETTERS ==========================
    public ActorView getNPCView(NPC npc){
        if(npc.isDead())
            return new ActorView("Dead");
        else if(npc.isHostile())
            return new ActorView("hostile");
        else if(npc.isAlly())
            return new ActorView("ally");
        else
            return new ActorView("neutral");
    }

    //====================== UPDATERS =========================
    public void updateNPCFrame(NPC npc){
        ActorView actorView = getNPCView(npc);
        String colorString = "#"+actorView.getFill().toString().substring(2);
        gameController.getGameView().getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorLabel().setText(npc.getName());
        gameController.getGameView().getActorHProgressBar().setProgress(npc.getHp()/100.0);
    }

    public void updatePlayerFrame(){
        //0xffab8d devient #ffab8d car c'est le format que comprend javafx:
        String colorString = "#"+gameController.getPlayerView().getFill().toString().substring(2);
        gameController.getGameView().getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorLabel().setText(gameController.getPlayerModel().getName());
        gameController.getGameView().getActorHProgressBar().setProgress(gameController.getPlayerModel().getHp()/100.0);
    }

    public void updatePlayerView(){
        if(gameController.getPlayerModel().isDead())
            gameController.getPlayerView().setFill(Color.LIGHTGRAY);
    }

    public void onNPCDeath(Actor npc){
            while(npc.getInventory().getItems().length != 0) {
                int[] availableRandPosInRoom = gameController.getRoomController().getCurrentRoomView().getRandPos();
                Scalar2D droppedPos = new Scalar2D(availableRandPosInRoom[0], availableRandPosInRoom[1]);
                npc.getInventory().getItems()[0].setScalar2D(droppedPos);
                npc.drop(npc.getInventory().getItems()[0]);
            }
            gameController.getRoomController().updateRoomView(DEFAULT_ROOMS_SIZE.getScalar2DCol(), DEFAULT_ROOMS_SIZE.getScalar2DLine());
    }

    // ================== ATTACK
    public void attack() {
        String actorTag = gameController.getGameView().getActorLabel().getText();
        Actor target = gameController.getRoomController().getCurrentRoomModel().getActor(actorTag);

        gameController.getPlayerModel().attack(target);
        if (target instanceof NPC) {
            updateNPCFrame((NPC) target);
            gameController.getRoomController().getCurrentRoomView().getFromRoom(actorTag).setFill(getNPCView((NPC) target).getFill());

            if(target.isDead())
                onNPCDeath(target);

        } else {
            updatePlayerFrame();
            updatePlayerView();
            gameController.isGameOver();
        }
    }

    //Gestion de l'ordinateur:
    public void resetActorPanel(){
        gameController.getGameView().getStoryBox().getChildren().remove(0);
        gameController.getGameView().setActorVBox(actorPanel);
        gameController.getGameView().getStoryBox().getChildren().add(0, actorPanel);
    }
}
