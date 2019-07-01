package сore.nodes;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class DraggablePane extends Pane {
    ObjectProperty<Node> draggableElement;

    public Node getDraggableElement() {
        return draggableElement.get();
    }

    public ObjectProperty<Node> draggableElementProperty() {
        return draggableElement;
    }

    public void setDraggableElement(Node draggableElement) {
        this.draggableElement.set(draggableElement);
    }

    public DraggablePane() {

    }
}
