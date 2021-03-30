package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SisSceneController
{
    @FXML
    private TextField dialogTextField;

    @FXML
    private void printHelp()
    {
        dialogTextField.setText("Hello World!");
    }
}
