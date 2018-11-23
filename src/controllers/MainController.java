package controllers;

import controllers.nodes.EmptyNode;
import controllers.nodes.Event;
import controllers.nodes.Node;
import controllers.nodes.Output;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
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
            if (draggedNode !=null) {
                draggedNode.setTranslatePosition(event.getSceneX(), event.getSceneY(), true);
            }
            if (draggedOut!=null){
                draggedOut.setConnectorPosition(event.getSceneX(), event.getSceneY());
            }
            event.consume();
        });

        eventsRoot.setOnDragDropped(event -> {
            draggedNode = null;
            draggedOut = null;
        });

        addEmptyNode.setOnAction(event -> {
            eventsRoot.getChildren().addAll(new EmptyNode(ecmCallX.get(), ecmCallY.get()));
        });
        addEventNode.setOnAction(event -> {
            eventsRoot.getChildren().add(new Event(ecmCallX.get(), ecmCallY.get()));
        });
    }

    public void spawnNewConnectionCurve(AnchorPane container, Rectangle connector){
        CubicCurve curve = new CubicCurve();

        curve.controlX1Property().bind(Bindings.add(curve.startXProperty(), 100));
        curve.controlX2Property().bind(Bindings.add(curve.endXProperty(), 100));
        curve.controlY1Property().bind(Bindings.add(curve.startYProperty(), 100));
        curve.controlY2Property().bind(Bindings.add(curve.endYProperty(), 100));

        curve.toBack();

        curve.startXProperty().bind(container.translateXProperty());
        curve.startYProperty().bind(container.translateYProperty());

        curve.endXProperty().bind(connector.translateXProperty());
        curve.endYProperty().bind(connector.translateYProperty());

        curve.setFill(Color.rgb(0,0,0,0));
        curve.setStroke(Color.rgb(0,0,0,1));

        eventsRoot.getChildren().add(curve);
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
}
