package pawn;

import controllers.MainController;
import controllers.nodes.EmptyNode;
import controllers.nodes.Node;
import controllers.nodes.events.Event;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import main.Main;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Location extends Button {

    public static final int LOCATION_ICON_HEIGHT = 64;

    private HashMap<String, Node> nodes = new HashMap<>();

    private Pane sequenceRoot;
    private ContextMenu sequenceRootContextMenu;

    public Location(String name, ImageView image) {
        super(name, image);
        setContentDisplay(ContentDisplay.TOP);

        sequenceRoot = new Pane();
        sequenceRootContextMenu = new ContextMenu();

        AtomicReference<Double> contextMenuCallX = new AtomicReference<>((double) 0);
        AtomicReference<Double> contextMenuCallY = new AtomicReference<>((double) 0);
        sequenceRoot.setOnContextMenuRequested(event -> {
            sequenceRootContextMenu.show(sequenceRoot, event.getScreenX(), event.getScreenY());
            contextMenuCallX.set(event.getX());
            contextMenuCallY.set(event.getY());
        });

        sequenceRoot.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                getController().setSelectedNode(null);
            }
            if (sequenceRootContextMenu.isShowing()){
                sequenceRootContextMenu.hide();
            }
        });

        Menu addNodeMenu = new Menu("Add event...");

        MenuItem addEmptyNode = new MenuItem("Empty node");
        addEmptyNode.setOnAction(event -> {
            sequenceRoot.getChildren().addAll(new EmptyNode(contextMenuCallX.get(), contextMenuCallY.get()));
        });


        MenuItem addEventNode = new MenuItem("Event");
        addEventNode.setOnAction(event -> {
            sequenceRoot.getChildren().add(new Event(contextMenuCallX.get(), contextMenuCallY.get()));
        });

        sequenceRootContextMenu.getItems().add(addNodeMenu);
        addNodeMenu.getItems().addAll(addEmptyNode,addEventNode);
    }

    private MainController getController() {
        return Main.getLoader().getController();
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    public Pane getSequenceRoot() {
        return sequenceRoot;
    }
}
