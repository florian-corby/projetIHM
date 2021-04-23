package controller;

import javafx.scene.paint.Color;
import model.Characters.Actor;
import model.Characters.NPC;
import model.Characters.Player;
import model.Location.Room;
import view.ActorView;
import view.GameView;
import view.RoomView;

public class ActorController {
    private final GameController gameController;
    private final GameView gameView;
    private final Player playerModel;
    private final ActorView playerView;
    private final InventoryController playerInvController;
    private Room currentRoomModel;
    private RoomView currentRoomView;

    //=============== CONSTRUCTEURS/INITIALISEURS ===============
    public ActorController(GameController c)
    {
        gameController = c;
        gameView = c.getGameView();
        playerModel = c.getGameModel().getShip().getPlayer();
        playerView = c.getPlayerView();
        playerInvController = c.getInventoryController();
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
        if(target instanceof NPC) {
            updateNPCFrame((NPC) target);
            updateNPCView((NPC) target);
        }
        else {
            updatePlayerFrame();
            updatePlayerView();
        }
    }
}
