package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import main.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainController {
    private BorderPane eventsEditor;
    @FXML
    public ImageView previewBackground;
    @FXML
    public Tab editorTab;
    @FXML
    public ChoiceBox edChoiceBox;

    public MainController() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        eventsEditor = loader.load(new FileInputStream("fxmls/Editor_Events.fxml"));
    }

    public void initialize(){
        editorTab.setContent(eventsEditor);
    }

    public BorderPane getEventsEditor() {
        return eventsEditor;
    }

    public Tab getEditorTab() {
        return editorTab;
    }
}
