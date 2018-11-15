package controllers.events;

import controllers.nodes.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;


public abstract class Event<Visual extends Pane> extends Node<Visual> {
    private Event[] childEvents;

    private FileInputStream editorFile;
    private FXMLLoader editorLoader;
    private BorderPane editor;
    private FOptionalEventController controller;

    Event(double x, double y, String fxmlPath, String editorPath, Event[] childEventsContainer) throws IOException {
        super(x,y,fxmlPath);

        editorLoader = new FXMLLoader();
        editorFile = new FileInputStream(editorPath);
        editor = editorLoader.load(editorFile);

        controller = editorLoader.getController();

        childEvents = childEventsContainer;

        getVisual().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                new EventEditor(editor);
            }
        });
    }

    public BorderPane getEditor() {
        return editor;
    }

    public Event[] getChildEvents() {
        return childEvents;
    }

    public FOptionalEventController getController() {
        return controller;
    }
}
