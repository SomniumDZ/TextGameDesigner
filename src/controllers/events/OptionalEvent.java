package controllers.events;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;

public class OptionalEvent extends VEvent {
    public OptionalEvent(double x, double y) throws IOException {
        super(x, y,"fxmls/VAEvent.fxml");
        getVisual().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                new EventEditor();
            }
        });
    }
}
