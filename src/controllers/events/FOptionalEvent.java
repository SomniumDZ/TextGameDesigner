package controllers.events;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.IOException;

public class FOptionalEvent extends VEvent<GridPane> {
    private FileInputStream editorFile;
    private FXMLLoader editorLoader;
    private BorderPane editor;
    private FOptionalEventController controller;
    private VEvent[] options;
    public FOptionalEvent(double x, double y) throws IOException {
        super(x, y, "fxmls/4OptionsEvent.fxml", new VEvent[4]);
        controller = new FOptionalEventController();

        options = new VEvent[4];

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
