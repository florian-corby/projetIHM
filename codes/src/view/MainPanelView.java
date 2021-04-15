package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import model.Game.MessageListener;


public class MainPanelView implements MessageListener
{
    //====================== ATTRIBUTS FXML ==========================
    @FXML
    private Button helpButton;
    @FXML
    private HBox sceneHBox;
    @FXML
    private TextArea dialogTextArea;
    @FXML
    private HBox mapPane;

    //====================== ATTRIBUTS ===============================
    private String previousDialog = "";
    private String helpMessage = "";

    //====================== GETTERS ==========================
    public String getHelpMessage()
    {
        return helpMessage;
    }

    public HBox getMapPane() {
        return mapPane;
    }

    public HBox getScene()
    {
        return sceneHBox;
    }

    //====================== AUTRES ==========================
    @FXML
    private void printHelp()
    {
        String currentDialog = dialogTextArea.getText();

        if(!currentDialog.equals(helpMessage)) {
            previousDialog = currentDialog;
            dialogTextArea.setText(helpMessage);
            helpButton.setText("Back to the Game");
        }

        else {
            helpButton.setText("?");
            dialogTextArea.setText(previousDialog);
        }
    }

    @Override
    public void update(String message) {
        dialogTextArea.setText(message);
    }

    public void setHelpMessage(String message)
    {
        helpMessage = message;
    }
}
