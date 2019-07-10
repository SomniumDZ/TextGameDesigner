package service;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;

public class ModalWindow {
    //debug option
    public static void fire(String text){
        Stage stage = new Stage();
        Label message = new Label(text);
        Button okButton = new Button("Ok");
        HBox messageBox = new HBox(10, message);
        HBox buttonsBox = new HBox(10, okButton);
        VBox root = new VBox(10, messageBox, buttonsBox);

        okButton.setDefaultButton(true);
        okButton.setOnAction(event -> stage.close());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Modal window");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
