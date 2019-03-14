package customs;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.layout.Pane;

public class PaneUp extends Pane {
    public final ObjectProperty<ContextMenu> contextMenuProperty(){
        if (contextMenu == null){
            contextMenu = new SimpleObjectProperty<>();
        }
        return contextMenu;
    }
    private ObjectProperty<ContextMenu> contextMenu;
    public final void setContextMenu(ContextMenu value){
        contextMenuProperty().set(value);
    }
    public final ContextMenu getContextMenu(){
        return contextMenu==null?null:contextMenu.get();
    }

    public PaneUp() {
        setOnContextMenuRequested(event -> contextMenu.get().show(this, event.getScreenX(), event.getScreenY()));
    }
}
