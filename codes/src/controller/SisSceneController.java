package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.Game.MessageListener;


public class SisSceneController implements MessageListener
{
    @FXML
    private VBox dialogPanel;

   /* @FXML
    private void saveGame()
    {
        try {
            FileOutputStream fileOut = new FileOutputStream("saveData.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(gameSISController.getShip());
            oos.close();
            dialogTextField.setText(dialogTextField.getText() + "\n\n --- You successfully saved the game! --- ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void update(String message) {
        //dialogPanel.getDialogTextArea().setText(message);
    }
}
