package controller;

import controller.Game.SISController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SisSceneController
{
    private SISController gameSISControllerController;

    @FXML
    private TextArea dialogTextField;

    @FXML
    private void printHelp()
    {
        dialogTextField.setText("Hello World!");
    }

    @FXML
    private void saveGame()
    {
        try {
            FileOutputStream fileOut = new FileOutputStream("saveData.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(gameSISControllerController.getShip());
            oos.close();
            System.out.println("You successfully saved the game!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TextArea getDialogTextArea() {
        return dialogTextField;
    }

    public void setGame(SISController sisController)
    {
        gameSISControllerController = sisController;
    }
}
