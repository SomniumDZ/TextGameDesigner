package customs;

import javafx.fxml.FXMLLoader;

import java.io.FileInputStream;
import java.io.IOException;

public class Event extends DraggableNode {

    public Event() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load(new FileInputStream("fxmls/Event.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
