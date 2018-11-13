package controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public MenuItem btnRunPreview;
    @FXML
    public Tab editorTab;
    @FXML
    public Pane eventsRoot;

    public MainController() {

    }

    public void initialize(){
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
    }
}
