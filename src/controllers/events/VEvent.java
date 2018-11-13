package controllers.events;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.VBox;



public class VEvent extends VBox {
    Image borderImage;
    BorderImage border;

    public VEvent(double x, double y) {
        this.setStyle("-fx-border-image-source: url(Border.png);" +
                "-fx-border-image-slice: 6px;" +
                "-fx-border-image-insets: 0px;" +
                "-fx-border-image-repeat: repeat;" +
                "-fx-border-image-width: 6px");
        setTranslateX(x);
        setTranslateY(y);
    }
}
