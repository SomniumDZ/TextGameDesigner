package controllers;

import controllers.events.OptionalEvent;
import controllers.events.VEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static main.Main.*;

public class MainController {
    @FXML
    public MenuItem btnRunPreview;
    @FXML
    public Tab editorTab;
    @FXML
    public AnchorPane eventsRoot;

    private MenuItem addEvent;
    private ContextMenu eventsContextMenu;

    public MainController() {
        eventsContextMenu = new ContextMenu();
        addEvent =  new MenuItem("Add event");
        eventsContextMenu.getItems().add(addEvent);
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
        addEvent.setOnAction(event -> {
            try {
                eventsRoot.getChildren().addAll(new OptionalEvent(ecmCallX.get(), ecmCallY.get()).getVisual());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
