package controllers.events;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.IOException;

public class OptionalEvent extends VEvent<GridPane> {
    private FileInputStream editorFile;
    private FXMLLoader editorLoader;
    private BorderPane editor;
    private OptionalEventController controller;
    public OptionalEvent(double x, double y) throws IOException {
        super(x, y, "fxmls/4OptionsEvent.fxml");
        controller = new OptionalEventController();

        editorLoader = new FXMLLoader();
        editorFile = new FileInputStream("fxmls/4OptionsEventEditor.fxml");
        editor = editorLoader.load(editorFile);



        getVisual().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                new EventEditor(editor);
            }
        });
    }
}
