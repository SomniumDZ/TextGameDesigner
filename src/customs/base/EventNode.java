package customs.base;

import customs.DraggableNode;
import javafx.fxml.FXMLLoader;
import unnamed.EventElement;

import java.io.FileInputStream;
import java.io.IOException;

public class EventNode extends DraggableNode<EventElement> {
    public EventNode() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        try {
            loader.load(new FileInputStream("fxmls/Event.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
