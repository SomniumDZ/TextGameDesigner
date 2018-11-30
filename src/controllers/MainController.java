package controllers;

import controllers.nodes.EmptyNode;
import controllers.nodes.Node;
import controllers.nodes.Output;
import controllers.nodes.events.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Preview;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class MainController {
    @FXML
    public MenuItem btnRunPreview;
    @FXML
    public Tab editorTab;

    @FXML
    public AnchorPane sequenceEditorRoot;
    @FXML
    public VBox sequenceEditorTools;

    private HashMap<String, Node> nodes = new HashMap<>();

    private Node chosenNode;
    private VBox eventToolBox = new VBox();

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
        sequenceEditorRoot.setOnContextMenuRequested(event -> {
            eventsContextMenu.show(sequenceEditorRoot, event.getScreenX(), event.getScreenY());
            ecmCallX.set(event.getX());
            ecmCallY.set(event.getY());
        });

        sequenceEditorRoot.setOnMouseClicked(event -> {
            chosenNode = null;
            nodes.forEach((s, node) -> node.setEffect(null));
            if (eventsContextMenu.isShowing()){
                eventsContextMenu.hide();
            }
        });

        sequenceEditorRoot.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if (draggedNode !=null) {
                draggedNode.setTranslatePosition(event.getSceneX(), event.getSceneY(), true);
            }
            if (draggedOut!=null){
                draggedOut.setConnectorPosition(event.getSceneX(), event.getSceneY());
            }
            event.consume();
        });

        sequenceEditorRoot.setOnDragDropped(event -> {
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
            sequenceEditorRoot.getChildren().addAll(new EmptyNode(ecmCallX.get(), ecmCallY.get()));
        });
        addEventNode.setOnAction(event -> {
            sequenceEditorRoot.getChildren().add(new Event(ecmCallX.get(), ecmCallY.get()));
        });

        initialNode = new Event(50,50);
        sequenceEditorRoot.getChildren().add(initialNode);


    }

    public void setChosenNode(Node node) {
        chosenNode = node;
        nodes.forEach((s, node1) -> node1.setEffect(null));
        if (chosenNode != null) {
            DropShadow effect = new DropShadow(15, Color.DARKORANGE);
            chosenNode.setEffect(effect);
        }
        if (chosenNode != null) {
            switch (chosenNode.getClass().getSimpleName()){
                case "Event":
                    showEventTools();
            }
        }

    }

    private void showEventTools(){
        TitledPane root = new TitledPane("Event", eventToolBox);
        sequenceEditorTools.getChildren().add(root);
    }

    public AnchorPane getSequenceEditorRoot() {
        return sequenceEditorRoot;
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
