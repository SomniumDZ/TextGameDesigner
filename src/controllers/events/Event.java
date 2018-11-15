package controllers.events;

import controllers.nodes.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;


public abstract class Event<Visual extends Pane, VC extends VisualController> extends Node<Visual, VC> {
    private Event[] childEvents;

    private FXMLLoader editorLoader;
    private BorderPane editor;

    Event(double x, double y, String fxmlPath, String editorPath, Event[] childEventsContainer) throws IOException {
        super(x,y,fxmlPath);

        editorLoader = new FXMLLoader();
        FileInputStream editorFile = new FileInputStream(editorPath);
        editor = editorLoader.load(editorFile);

        childEvents = childEventsContainer;

        EventEditor eventEditor = new EventEditor(editor);

        getVisual().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                eventEditor.getStage().showAndWait();
            }
        });


        eventEditor.getStage().setOnCloseRequest(event -> onEditorCloses());

    }


    abstract void onEditorCloses();

    public BorderPane getEditor() {
        return editor;
    }

    public Event[] getChildEvents() {
        return childEvents;
    }

    public EventEditorController getController() {
        return editorLoader.getController();
    }
}
