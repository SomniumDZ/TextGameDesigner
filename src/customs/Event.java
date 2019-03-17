package customs;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Event extends DraggableNode {

    public Event() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Event.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
