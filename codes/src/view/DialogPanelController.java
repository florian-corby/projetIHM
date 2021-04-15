package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DialogPanelController {
    @FXML
    private TextArea dialogTextArea;

    public TextArea getDialogTextArea() {
        return dialogTextArea;
    }

    public void updateMessage(String message)
    {
        dialogTextArea.setText(message);
    }
}
