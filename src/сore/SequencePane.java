package сore;

import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;

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
}
