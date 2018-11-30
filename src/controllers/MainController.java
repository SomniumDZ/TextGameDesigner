package controllers;

import controllers.nodes.EmptyNode;
import controllers.nodes.Node;
import controllers.nodes.Output;
import controllers.nodes.events.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Preview;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class MainController {
    @FXML
    public MenuItem btnRunPreview;
    @FXML
    public Tab editorTab;

    @FXML
    public AnchorPane editorRoot;
    @FXML
    public VBox editorTools;

    private HashMap<String, Node> nodes = new HashMap<>();

    private MenuItem addEmptyNode;
    private MenuItem addEventNode;
    private ContextMenu eventsContextMenu;
    private Node draggedNode;
    private Output draggedOut;
    private Node initialNode;

    public MainController() {
        eventsContextMenu = new ContextMenu();
        Menu addEvent = new Menu("Add event...");
        addEmptyNode = new MenuItem("Empty node");
        addEventNode = new MenuItem("Event");
        eventsContextMenu.getItems().add(addEvent);
        addEvent.getItems().addAll(addEmptyNode,addEventNode);
    }



    public void initialize(){
        AtomicReference<Double> ecmCallX = new AtomicReference<>((double) 0);
        AtomicReference<Double> ecmCallY = new AtomicReference<>((double) 0);
        btnRunPreview.setOnAction(event -> {
            new Preview(initialNode);
        });
        editorRoot.setOnContextMenuRequested(event -> {
            eventsContextMenu.show(editorRoot, event.getScreenX(), event.getScreenY());
            ecmCallX.set(event.getX());
            ecmCallY.set(event.getY());
        });

        editorRoot.setOnMouseClicked(event -> {
            if (eventsContextMenu.isShowing()){
                eventsContextMenu.hide();
            }
        });

        editorRoot.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if (draggedNode !=null) {
                draggedNode.setTranslatePosition(event.getSceneX(), event.getSceneY(), true);
            }
            if (draggedOut!=null){
                draggedOut.setConnectorPosition(event.getSceneX(), event.getSceneY());
            }
            event.consume();
        });

        editorRoot.setOnDragDropped(event -> {
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

        addEmptyNode.setOnAction(event -> {
            editorRoot.getChildren().addAll(new EmptyNode(ecmCallX.get(), ecmCallY.get()));
        });
        addEventNode.setOnAction(event -> {
            editorRoot.getChildren().add(new Event(ecmCallX.get(), ecmCallY.get()));
        });

        initialNode = new Event(50,50);
        editorRoot.getChildren().add(initialNode);
    }

    public AnchorPane getEventsRoot() {
        return editorRoot;
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

    public HashMap<String, Node> getNodeMap() {
        return nodes;
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node node) {
        initialNode = node;
    }
}
