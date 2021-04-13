package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import model.Game.MessageListener;


public class SisSceneController implements MessageListener
{
    private String previousDialog = "";

    @FXML
    private Button helpButton;

    @FXML
    private TextArea dialogTextField;

    @FXML
    private void printHelp()
    {
        String currentDialog = dialogTextField.getText();

        String helpDialog = """
                -- User Manual of Silent In Space --\s

                WELCOME to Silent In Space! This game was developed by Florian Legendre, Alexis Louail
                and Vincent Tourenne as a universitary project. This is a demo, hence all the features
                intended to be in the final version aren't there. This game is meant to be played by
                textual commands. Meaning that you must input valid commands with your keyboard and
                the game will react accordingly. For a thorough listing of commands, their syntaxes
                and effects, type help! Enjoy!

                SCENARIO: You wake up in an alien ship. You understand that you've been abducted and
                you must escape. Yet, you can't use the escape pods of the ship without a code.
                Umhon, an important alien person, can give you this code (OR you can take it from her
                body) but you have to bring her the proof of the abominable experiments being conducted
                on humans. This proof is what will end the abductions and possibly the end of humanity.
                The escape room is ROOM 13. Good luck human!

                """;

        if(!currentDialog.equals(helpDialog)) {
            previousDialog = currentDialog;
            dialogTextField.setText(helpDialog);
            helpButton.setText("Back to the Game");
        }

        else {
            helpButton.setText("?");
            dialogTextField.setText(previousDialog);
        }
    }

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

    public TextArea getDialogTextArea() {
        return dialogTextField;
    }

    @Override
    public void update(String message) {
        dialogTextField.setText(message);
    }
}
