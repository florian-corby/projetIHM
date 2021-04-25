package controller;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.util.Duration;
import model.Characters.Actor;
import javafx.scene.layout.VBox;
import model.Characters.NPC;
import model.Utils.Scalar2D;
import view.ActorView;

import static controller.GameController.DEFAULT_ROOMS_SIZE;

/** -----------------------------------------------------------------------------
 * Contrôleur des acteurs du jeu:
 *
 * Rôle: Contrôleur global du jeu qui gère les acteurs (les npcs et le joueur) du
 * jeu ainsi que le panneau qui leur est dédié dans la vue du jeu
 * ------------------------------------------------------------------------------ */

public class ActorController {
    private final GameController gameController;

    //Gestion de l'ordinateur:
    private final VBox initialActorPanel;


    //=============== CONSTRUCTEURS/INITIALISEURS ===============

    public ActorController(GameController c) {
        gameController = c;
        initialActorPanel = c.getGameView().getActorVBox();

        //Déplacement aléatoire des personnages:
        ScheduledService<Void> moveNPCsService = new ScheduledService<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        Platform.runLater(() -> {
                            gameController.getRoomController().unloadNPCs();
                            gameController.getRoomController().loadNPCs();
                        });
                        return null;
                    }
                };
            }
        };

        moveNPCsService.setPeriod(Duration.seconds(2));
        moveNPCsService.start();
    }

    //====================== GETTERS ==========================
    public VBox getInitialActorPanel() { return initialActorPanel; }

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

    // Mise a jour de la Vue pour le cadre des NPCs (barre de vie, couleur du cadre, image)
    public void updateNPCFrame(NPC npc){
        ActorView actorView = getNPCView(npc);
        String colorString = "#"+actorView.getFill().toString().substring(2);
        gameController.getGameView().getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorLabel().setText(npc.getName());
        gameController.getGameView().getActorImageView().setImage(new Image(getClass().getResource("../img/alien.png").toString(), true));
        gameController.getGameView().getActorHProgressBar().setProgress(npc.getHp()/100.0);
    }
    // Mise a jour de la Vue pour le cadre du Joueur (barre de vie, couleur du cadre, image)
    public void updatePlayerFrame(){
        //0xffab8d devient #ffab8d car c'est le format que comprend javafx:
        String colorString = "#"+gameController.getPlayerView().getFill().toString().substring(2);
        gameController.getGameView().getActorVBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorBtnHBox().setStyle("-fx-border-color:"+colorString+";");
        gameController.getGameView().getActorLabel().setText(gameController.getPlayerModel().getName());
        gameController.getGameView().getActorImageView().setImage(new Image(getClass().getResource("../img/main_character.png").toString(), true));
        gameController.getGameView().getActorHProgressBar().setProgress(gameController.getPlayerModel().getHp()/100.0);
    }

    // Mise a jour du Joueur sur la Carte
    public void updatePlayerView(){
        if(gameController.getPlayerModel().isDead())
            gameController.getPlayerView().setFill(Color.LIGHTGRAY);
    }

    // Gestion de la mort d'un personnage et des items qu'il laisse tomber
    public void onNPCDeath(Actor npc){
        while(npc.getInventory().getItems().length != 0) {
            //On redonne une position aléatoire à l'objet qui va être droppé au sol:
            int[] availableRandPosInRoom = gameController.getRoomController().getCurrentRoomView().getRandPos();
            Scalar2D droppedPos = new Scalar2D(availableRandPosInRoom[0], availableRandPosInRoom[1]);
            npc.getInventory().getItems()[0].setScalar2D(droppedPos);

            //On met à jour le modèle:
            npc.drop(npc.getInventory().getItems()[0]);
        }

        gameController.getRoomController().updateRoomView(DEFAULT_ROOMS_SIZE.getScalar2DCol(), DEFAULT_ROOMS_SIZE.getScalar2DLine());
    }

    //====================== ATTACK  =========================

    // Gestion de l'attaque sur un personnage (joueur ou npcs)
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

    //====================== RESET =========================

    // Gestion de l'ordinateur:
    public void resetActorPanel(){
        gameController.getGameView().getStoryBox().getChildren().remove(0);
        gameController.getGameView().setActorVBox(initialActorPanel);
        gameController.getGameView().getStoryBox().getChildren().add(0, initialActorPanel);
    }
}
