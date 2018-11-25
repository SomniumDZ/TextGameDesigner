package controllers.nodes.events;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class EventEditor extends BorderPane {
    public EventEditor() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/EventEditor.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
