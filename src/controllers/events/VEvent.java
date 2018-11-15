package controllers.events;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public abstract class VEvent <Visual extends Pane>{
    private VEvent[] childEvents;
    private FXMLLoader optionalLoader;
    private Visual visual;

    public VEvent(double x, double y, String fxmlPath, VEvent[] childEventsContainer) throws IOException {
        optionalLoader = new FXMLLoader();
        this.visual = optionalLoader.load(new FileInputStream(fxmlPath));
        visual.setStyle("-fx-background-color: #adadad;"
//               +"-fx-border-image-source: url(Border.png);" +
//                "-fx-border-image-slice: 6px;" +
//                "-fx-border-image-insets: 0px;" +
//                "-fx-border-image-repeat: repeat;" +
//                "-fx-border-image-width: 6px"
        );
        visual.setTranslateX(x);
        visual.setTranslateY(y);
        childEvents = childEventsContainer;
    }

    public VEvent() {
    }

    public Visual getVisual() {
        return visual;
    }

    public VEvent[] getChildEvents() {
        return childEvents;
    }
}
