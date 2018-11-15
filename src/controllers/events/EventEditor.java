package controllers.events;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static main.Main.ew;

public class EventEditor {
    @FXML
    public Tab editorTab;

    FOptionsEventsEditorController controller;

    private BorderPane root;
    private BorderPane editor;

    private Stage stage;


    EventEditor(BorderPane editor) {
        Scene scene;

        FXMLLoader rootLoader = new FXMLLoader();
        FileInputStream edEventsRoot;
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
        this.editor = editor;

        controller = rootLoader.getController();

        controller.getEditorTab().setContent(new BorderPane(editor));

        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }
}
