package view;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class MapPanelController {
    @FXML
    private GridPane room;

    public GridPane getRoomView(){
        return room;
    }
}
