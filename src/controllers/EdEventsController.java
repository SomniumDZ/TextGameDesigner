package controllers;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import main.Main;

public class EdEventsController {
    public ChoiceBox edChoiceBox;

    public EdEventsController() {
    }

    public void initialize(){
        edChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int index = (int) newValue;
            switch (index){
                case 0:
                    break;
                case 1:
                    Main.getLoader().<MainController>getController().getEditorTab().setContent(
                            Main.getLoader().<MainController>getController().getItemsEditor()
                    );
            }
        });
    }
}
