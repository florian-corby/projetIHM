package silent_in_space.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ContainerView extends Polygon
{
    private final Double[] hexagonPoints = {82.0, 32.0, 86.0, 26.0, 94.0, 26.0, 98.0, 32.0, 94.0, 40.0, 86.0, 40.0};

    // Création de la vu d'un conteneur
    public ContainerView()
    {
        this.getPoints().addAll(hexagonPoints);
        this.setFill(Color.RED);
    }

    // Création de la vue d'un conteneur specifié par un catégorie
    public ContainerView(String containerCategory)
    {
        this.getPoints().addAll(hexagonPoints);

        if ("HealthStation".equals(containerCategory)) {
            this.setFill(Color.MAGENTA);
        } else {
            this.setFill(Color.LIGHTGRAY);
        }
    }
}
