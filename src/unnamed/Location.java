package unnamed;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class Location {
    HashMap<String, Node> nodes;
    Pane canvas;

    public Location(String UID, ComboBox locationComboBox) {
        nodes = new HashMap<>();
        locationComboBox.getItems().add(UID);
        canvas = new Pane();
    }

    public Pane getCanvas() {
        return canvas;
    }
}
