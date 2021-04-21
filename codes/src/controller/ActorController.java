package controller;

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
}
