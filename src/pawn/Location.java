package pawn;

import controllers.MainController;
import controllers.nodes.EmptyNode;
import controllers.nodes.Node;
import controllers.nodes.Output;
import controllers.nodes.events.Event;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import main.Main;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Location extends Button {

    public static final int LOCATION_ICON_HEIGHT = 64;

    private HashMap<String, Node> nodes = new HashMap<>();

    private Node draggedNode;
    private Output draggedOut;

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

        sequenceRoot.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if (draggedNode !=null) {
                draggedNode.setTranslatePosition(event.getSceneX(), event.getSceneY(), true);
            }
            if (draggedOut!=null){
                draggedOut.setConnectorPosition(event.getSceneX(), event.getSceneY());
            }
            event.consume();
        });

        sequenceRoot.setOnDragDropped(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            draggedNode = null;
            if (draggedOut!=null) {
                draggedOut.reset();
                draggedOut = null;
            }
            Dragboard db = event.getDragboard();
            db.clear();
            event.setDropCompleted(true);
            event.consume();
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

    public Node getDraggedNode() {
        return draggedNode;
    }

    public void setDraggedNode(Node draggedNode) {
        this.draggedNode = draggedNode;
    }

    public Output getDraggedOut() {
        return draggedOut;
    }

    public void setDraggedOut(Output draggedOut) {
        this.draggedOut = draggedOut;
    }

    public Pane getSequenceRoot() {
        return sequenceRoot;
    }
}
