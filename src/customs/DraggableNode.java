package customs;

import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@DefaultProperty("elements")
public class DraggableNode extends BorderPane {
    @FXML
    HBox header;
    @FXML
    Label titleLabel;
    @FXML
    VBox elements;
    @FXML
    Circle inputCircle;

    public DraggableNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/DraggableNode.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AtomicReference<Double> deltaX = new AtomicReference<>((double) 0);
        AtomicReference<Double> deltaY = new AtomicReference<>((double) 0);
        header.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            deltaX.set(getLayoutX() - mouseEvent.getSceneX());
            deltaY.set(getLayoutY() - mouseEvent.getSceneY());
            header.setCursor(Cursor.CLOSED_HAND);
            toFront();
        });
        header.setOnMouseReleased(mouseEvent -> header.setCursor(Cursor.HAND));
        header.setOnMouseDragged(mouseEvent -> {
            setLayoutX(mouseEvent.getSceneX() + deltaX.get());
            setLayoutY(mouseEvent.getSceneY() + deltaY.get());
        });
        header.setOnMouseEntered(mouseEvent -> header.setCursor(Cursor.HAND));

        inputCircle.setOnDragOver(event -> {
            System.out.println("over");
            event.acceptTransferModes(TransferMode.ANY);
        });
        inputCircle.setOnDragDropped(this::inputContact);
        inputCircle.setOnDragEntered(event -> System.out.println("entered"));
    }

    @FXML
    void initialize(){

    }

    private void inputContact(DragEvent event) {
        System.out.println("contacted with: "+event.getGestureSource());
    }

    public ObservableList<Node> getElements() {
        return elements.getChildren();
    }

    public final void setTitle(String value) {
        titleLabel.setText(value);
    }
    public final String getTitle() {
        return titleLabel.getText();
    }

}
