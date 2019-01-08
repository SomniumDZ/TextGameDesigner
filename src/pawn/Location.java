package pawn;

import controllers.nodes.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class Location extends Button {

    private HashMap<String, Node> nodes = new HashMap<>();
    private Pane sequenceRoot = new Pane();

    public Location(String name, ImageView image) {
        super(name, image);
        setContentDisplay(ContentDisplay.TOP);
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    public Pane getSequenceRoot() {
        return sequenceRoot;
    }
}
