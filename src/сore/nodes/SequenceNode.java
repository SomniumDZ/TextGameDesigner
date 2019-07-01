package сore.nodes;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class SequenceNode extends DraggablePane {
    public SequenceNode() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
