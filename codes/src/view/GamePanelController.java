package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class GamePanelController {

    private String previousDialog = "";
    private String helpMessage = "";

    @FXML
    private Button helpButton;

    @FXML
    private TextArea dialogTextField;

    public String getHelpMessage()
    {
        return helpMessage;
    }

    @FXML
    private void printHelp()
    {
        String currentDialog = mainPanelController.getDialogPanelController().getDialogTextArea().getText();

        if(!currentDialog.equals(mainPanelController.getGamePanelController().getHelpMessage())) {
            previousDialog = currentDialog;
            dialogTextField.setText(helpDialog);
            helpButton.setText("Back to the Game");
        }

        else {
            helpButton.setText("?");
            dialogTextField.setText(previousDialog);
        }
    }

    public void setHelpMessage(String message)
    {
        helpMessage = message;
    }
}
