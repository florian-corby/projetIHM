package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Game.MessageListener;


public class GameView implements MessageListener
{
    //====================== ATTRIBUTS FXML ==========================
    @FXML
    private HBox sceneHBox;

    // -------- Le panneau gauche (panneau de la map):
    @FXML private Button zoomPlusButton;
    @FXML private Button zoomMinusButton;
    @FXML private Label roomLabel;
    @FXML private Pane mapPane;
    @FXML private Slider mapHorizontalSlider;
    @FXML private Slider mapVerticalSlider;
    @FXML private Button backButton;

    // -------- Le panneau droit (panneau du joueur):
    @FXML private Label actorLabel;
    @FXML private ProgressBar actorHProgressBar;
    @FXML private ImageView actorImageView;
    @FXML private Button searchButton;
    @FXML private Button talkButton;
    @FXML private Button attackButton;
    @FXML private TextArea dialogTextArea;
    @FXML private Button saveButton;
    @FXML private Button helpButton;
    @FXML private Button loadButton;
    @FXML private VBox inventoryVBox;
    @FXML private Button giveButton;
    @FXML private Button dropButton;


    //====================== GETTERS ==========================
    public HBox getScene() { return sceneHBox; }

    // -------- Le panneau gauche (panneau de la map):
    public Button getZoomPlusButton() { return zoomPlusButton; }
    public Button getZoomMinusButton() { return zoomMinusButton; }
    public Label getRoomLabel() { return roomLabel; }
    public Pane getMapPane() {
        return mapPane;
    }
    public Slider getMapHorizontalSlider() { return mapHorizontalSlider; }
    public Slider getMapVerticalSlider() { return mapVerticalSlider; }
    public Button getBackButton() { return backButton; }

    // -------- Le panneau droit (panneau du joueur):
    public Label getActorLabel() { return actorLabel; }
    public ProgressBar getActorHProgressBar() { return actorHProgressBar; }
    public ImageView getActorImageView() { return actorImageView; }
    public Button getSearchButton() { return searchButton; }
    public Button getTalkButton() { return talkButton; }
    public Button getAttackButton() { return attackButton; }
    public TextArea getDialogTextArea() { return dialogTextArea; }
    public Button getSaveButton() { return saveButton; }
    public Button getHelpButton() { return helpButton; }
    public Button getLoadButton() { return loadButton; }
    public VBox getInventoryVBox() { return inventoryVBox; }
    public Button getGiveButton() { return giveButton; }
    public Button getDropButton() { return dropButton; }


    //====================== AUTRES ==========================
    @Override
    public void update(String message) {
        dialogTextArea.setText(message);
    }
}
