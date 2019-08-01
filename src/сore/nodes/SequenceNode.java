package сore.nodes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import service.ModalWindow;

import java.io.IOException;

public class SequenceNode extends BorderPane {
    @FXML
    HBox header;

    public SequenceNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxmls/SequenceNode.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            ModalWindow.fire("FXML file not found at "+loader.getLocation().toString());
        }

        initDraggability();
    }

    private void initDraggability() {
        header.setOnMouseDragged(event -> {
            setTranslateX(event.getSceneX());
            setTranslateY(event.getSceneY());
        });
    }

}
