package controller;

import javafx.scene.paint.Color;
import model.Characters.Actor;
import javafx.scene.layout.VBox;
import model.Characters.NPC;
import model.Characters.Player;
import view.ActorView;
import view.GameView;


/* -----------------------------------------------------------------------------
 * Contrôleur des acteurs du jeu:
 *
 * Rôle: Contrôleur global du jeu qui gère les acteurs (les npcs et le joueur) du
 * jeu ainsi que le panneau qui leur est dédié dans la vue du jeu
 * ----------------------------------------------------------------------------- */

public class ActorController {
    private final GameController gameController;
    private final GameView gameView;
    private final Player playerModel;
    private final ActorView playerView;

    //Gestion de l'ordinateur:
    private final VBox actorPanel;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public ActorController(GameController c) {
        gameController = c;
        gameView = c.getGameView();
        playerModel = c.getGameModel().getShip().getPlayer();
        playerView = c.getPlayerView();
        actorPanel = c.getGameView().getActorVBox();
    }

    //====================== UPDATERS =========================
    public void updateNPCFrame(NPC npc){
        ActorView actorView = ActorView.getNPCView(npc);
        String colorString = "#"+actorView.getFill().toString().substring(2);
        gameView.getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorLabel().setText(npc.getName());
        gameView.getActorHProgressBar().setProgress(npc.getHp());
    }

    public void updatePlayerFrame(){
        //0xffab8d devient #ffab8d car c'est c'est le format que comprend javafx:
        String colorString = "#"+playerView.getFill().toString().substring(2);
        gameView.getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameView.getActorLabel().setText(playerModel.getName());
        gameView.getActorHProgressBar().setProgress(playerModel.getHp());
    }

    public void updateNPCView(NPC npc){
        if(npc.isDead())
            ActorView.getNPCView(npc).setFill(Color.LIGHTGRAY);
        else if(npc.isHostile())
            ActorView.getNPCView(npc).setFill(Color.RED);
        else if(npc.isAlly())
            ActorView.getNPCView(npc).setFill(Color.LIME);
        else ActorView.getNPCView(npc).setFill(Color.DARKGOLDENROD);
    }

    public void updatePlayerView(){
        if(playerModel.isDead())
            playerView.setFill(Color.LIGHTGRAY);
    }

    // ================== ATTACK
    public void attack() {
        String actorTag = gameView.getActorLabel().getText();
        Actor target = gameController.getRoomController().getCurrentRoomModel().getActor(actorTag);

        playerModel.attack(target);
        if (target instanceof NPC) {
            updateNPCFrame((NPC) target);
            updateNPCView((NPC) target);
        } else {
            updatePlayerFrame();
            updatePlayerView();
        }
    }

    //Gestion de l'ordinateur:
    public void resetActorPanel(){
        gameController.getGameView().getStoryBox().getChildren().remove(0);
        gameController.getGameView().setActorVBox(actorPanel);
        gameController.getGameView().getStoryBox().getChildren().add(0, actorPanel);
    }
}
