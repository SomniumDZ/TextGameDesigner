package сore.nodes;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class SequenceNode extends DraggablePane {
    public SequenceNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/SequenceNode.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
