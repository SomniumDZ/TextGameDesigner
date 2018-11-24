package controllers;

import controllers.nodes.EmptyNode;
import controllers.nodes.Event;
import controllers.nodes.Node;
import controllers.nodes.Output;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class MainController {
    @FXML
    public MenuItem btnRunPreview;
    @FXML
    public Tab editorTab;

    @FXML
    public AnchorPane eventsRoot;

    private HashMap<String, Output> outputs = new HashMap<>();

    private MenuItem addEmptyNode;
    private MenuItem addEventNode;
    private ContextMenu eventsContextMenu;
    private static Node draggedNode;
    private static Output draggedOut;

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
            Stage preview = new Stage();
            Scene scene = null;
            try {
                scene = new Scene(new Preview().getRoot(), 600, 400);
            } catch (IOException e) {
                e.printStackTrace();
            }
            preview.setScene(scene);
            preview.showAndWait();
        });
        eventsRoot.setOnContextMenuRequested(event -> {
            eventsContextMenu.show(eventsRoot, event.getScreenX(), event.getScreenY());
            ecmCallX.set(event.getX());
            ecmCallY.set(event.getY());
        });

        eventsRoot.setOnMouseClicked(event -> {
            if (eventsContextMenu.isShowing()){
                eventsContextMenu.hide();
            }
        });

        eventsRoot.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            if (draggedNode !=null) {
                draggedNode.setTranslatePosition(event.getSceneX(), event.getSceneY(), true);
            }
            if (draggedOut!=null){
                draggedOut.setConnectorPosition(event.getSceneX(), event.getSceneY());
            }
            event.consume();
        });

        eventsRoot.setOnDragDropped(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            draggedNode = null;
            draggedOut = null;
            Dragboard db = event.getDragboard();
            db.clear();
            event.setDropCompleted(true);
            event.consume();
        });

        addEmptyNode.setOnAction(event -> {
            eventsRoot.getChildren().addAll(new EmptyNode(ecmCallX.get(), ecmCallY.get()));
        });
        addEventNode.setOnAction(event -> {
            eventsRoot.getChildren().add(new Event(ecmCallX.get(), ecmCallY.get()));
        });
    }

    public AnchorPane getEventsRoot() {
        return eventsRoot;
    }

    public static Node getDraggedNode() {
        return draggedNode;
    }

    public static void setDraggedNode(Node draggedNode) {
        MainController.draggedNode = draggedNode;
    }

    public static void setDraggedOut(Output draggedOut) {
        MainController.draggedOut = draggedOut;
    }

    public HashMap<String, Output> getNodeMap() {
        return outputs;
    }
}
