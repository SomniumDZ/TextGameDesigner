package controllers.events;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static main.Main.ew;

public class EventEditor {
    private BorderPane root;
    private FXMLLoader rootLoader;

    public EventEditor() {
        Stage stage;
        Scene scene;
        rootLoader = new FXMLLoader();
        FileInputStream edEventsRoot = null;
        try {
            edEventsRoot = new FileInputStream("fxmls/EventEditorRoot.fxml");
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
            ew.throwError("File not founded");
            return;
        }
        try {
            root = rootLoader.load(edEventsRoot);
        } catch (
                IOException e) {
            e.printStackTrace();
            return;
        }

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
