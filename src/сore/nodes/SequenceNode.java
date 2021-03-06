package сore.nodes;

import javafx.beans.DefaultProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.ModalWindow;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@DefaultProperty(value = "workspace")
public class SequenceNode extends BorderPane {
    @FXML
    HBox header;
    @FXML
    Label title;
    @FXML
    VBox workspace;

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
        //sequence pane in scene coordinates
        AtomicReference<Double> deltaX = new AtomicReference<>(0d);
        AtomicReference<Double> deltaY = new AtomicReference<>(0d);
        header.setOnMousePressed(event -> {
            deltaX.set(getLayoutX()-event.getSceneX()+getTranslateX());
            deltaY.set(getLayoutY()-event.getSceneY()+getTranslateY());
            toFront();
        });
        header.setOnMouseDragged(event -> {
            setTranslateX(event.getSceneX()+deltaX.get());
            setTranslateY(event.getSceneY()+deltaY.get());
        });
    }

    public ObservableList<Node> getWorkspace() {
        return workspace.getChildren();
    }
}
