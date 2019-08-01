package сore;

import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;
import сore.nodes.NodeType;
import сore.nodes.SequenceNode;

public class SequencePane extends Pane {
    private ContextMenu contextMenu;

    public SequencePane(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
        setOnContextMenuRequested(event -> {
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        });

    }

    public void setContextMenu(ContextMenu contextMenu) {
        this.contextMenu = contextMenu;
    }

    public void addNode(NodeType type) {
        switch (type) {
            case Event:
                getChildren().add(new SequenceNode());
                break;
            case Action:
                break;
        }
    }
}
