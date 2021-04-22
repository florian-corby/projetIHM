package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.Game.MessageListener;

public class GameView extends HBox implements MessageListener
{
    @FXML private HBox sceneHBox;

    // -------- Le panneau gauche (panneau de la map):
    @FXML private Button zoomPlusButton;
    @FXML private Button zoomMinusButton;
    @FXML private Label roomLabel;
    @FXML private Pane mapPane;
    @FXML private Slider mapHorizontalSlider;
    @FXML private Slider mapVerticalSlider;

    // -------- Le panneau droit (panneau du joueur):
    @FXML private VBox storyBox;
    @FXML private VBox actorVBox;
    @FXML private Label actorLabel;
    @FXML private ProgressBar actorHProgressBar;
    @FXML private ImageView actorImageView;
    @FXML private HBox actorBtnHBox;
    @FXML private Button searchButton;
    @FXML private Button attackButton;
    @FXML private TextArea dialogTextArea;
    @FXML private Button saveButton;
    @FXML private Button helpButton;
    @FXML private Button loadButton;
    @FXML private VBox inventoryVBox;
    @FXML private Button giveButton;
    @FXML private Button dropButton;

    //====================== GETTERS ==========================
    public HBox getSceneHBox() { return sceneHBox; }

    // -------- Le panneau gauche (panneau de la map):
    public Button getZoomPlusButton() { return zoomPlusButton; }
    public Button getZoomMinusButton() { return zoomMinusButton; }
    public Label getRoomLabel() { return roomLabel; }
    public Pane getMapPane() {
        return mapPane;
    }
    public Slider getMapHorizontalSlider() { return mapHorizontalSlider; }
    public Slider getMapVerticalSlider() { return mapVerticalSlider; }

    // -------- Le panneau droit (panneau du joueur):
    public VBox getStoryBox() { return storyBox; }
    public VBox getActorVBox() { return actorVBox; }
    public Label getActorLabel() { return actorLabel; }
    public ProgressBar getActorHProgressBar() { return actorHProgressBar; }
    public ImageView getActorImageView() { return actorImageView; }
    public HBox getActorBtnHBox() { return actorBtnHBox; }
    public Button getSearchButton() { return searchButton; }
    public Button getAttackButton() { return attackButton; }
    public TextArea getDialogTextArea() { return dialogTextArea; }
    public Button getSaveButton() { return saveButton; }
    public Button getHelpButton() { return helpButton; }
    public Button getLoadButton() { return loadButton; }
    public VBox getInventoryVBox() { return inventoryVBox; }
    public Button getGiveButton() { return giveButton; }
    public Button getDropButton() { return dropButton; }

    //====================== SETTERS ==========================
    public void setActorVBox(VBox replacer){ actorVBox = replacer; }
    public void setMapPaneClip(){
        //Pour que la pièce passe derrière la fenêtre si débordement:
        final Rectangle clipPane = new Rectangle();
        this.getMapPane().setClip(clipPane);
        this.getMapPane().layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipPane.setWidth(newValue.getWidth());
            clipPane.setHeight(newValue.getHeight());
        });
    }
    public void setCenteredSlidersOnWindowRedimensionned(){
        //Les sliders réinitialisent la pièce au centre du panneau à chaque redimensionnement de la fenêtre:
        this.getMapHorizontalSlider().maxProperty().addListener((observableValue, number, t1) ->
                this.getMapHorizontalSlider().setValue(this.getMapHorizontalSlider().getMax()/2));
        this.getMapVerticalSlider().maxProperty().addListener((observableValue, number, t1) ->
                this.getMapVerticalSlider().setValue(this.getMapVerticalSlider().getMax()/2));
    }


    //====================== AUTRES ==========================
    @Override
    public void update(String message) {
        dialogTextArea.setText(dialogTextArea.getText() + "\n\n" + message);
        dialogTextArea.selectPositionCaret(dialogTextArea.getLength());
        dialogTextArea.deselect();
    }
}
