package controllers;

import controllers.nodes.EmptyNode;
import controllers.nodes.Node;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class MainController {
    @FXML
    public MenuItem btnRunPreview;
    @FXML
    public Tab editorTab;

    @FXML
    public AnchorPane eventsRoot;

    private MenuItem addEmptyNode;
    private ContextMenu eventsContextMenu;
    private static Node draggedNode;

    public MainController() {
        eventsContextMenu = new ContextMenu();
        Menu addEvent = new Menu("Add event...");
        addEmptyNode = new MenuItem("Empty node");
        eventsContextMenu.getItems().add(addEvent);
        addEvent.getItems().addAll(addEmptyNode);
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
            draggedNode.setTranslatePosition(event.getSceneX(), event.getSceneY());
            event.consume();
        });

        addEmptyNode.setOnAction(event -> {
            eventsRoot.getChildren().addAll(new EmptyNode(ecmCallX.get(), ecmCallY.get()));
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
}
