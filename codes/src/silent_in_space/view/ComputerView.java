package silent_in_space.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ComputerView extends VBox {
    //====================== ATTRIBUTS ==========================
    @FXML private VBox computer;
    @FXML private GridPane computerDesk;
    @FXML private Button eventBtn;
    @FXML private Button quitBtn;

    //====================== GETTERS ==========================
    public VBox getComputer(){return computer;}
    public GridPane getComputerDesk() { return computerDesk; }
    public Button getEventBtn() { return eventBtn; }
    public Button getQuitBtn() { return quitBtn; }
}
