package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class ItemView extends Ellipse
{
    // Création visuelle d'un item
    public ItemView()
    {
        setRadiusX(5);
        setRadiusY(10);
        setFill(Color.BLACK);
    }
}
