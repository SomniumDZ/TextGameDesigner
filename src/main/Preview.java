package main;

import controllers.nodes.events.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static main.Main.ew;

public class Preview extends BorderPane {
    @FXML
    private Label eventMessage;
    @FXML
    private VBox outputsBox;

    public Preview(Node initialNode) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Preview.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("FXML read error");
        }

        // FIXME: 28.11.2018 load Node that MARKED initial
        loadEvent((Event) initialNode);
        show();
    }

    private void loadEvent(Event event){
        eventMessage.setText(event.getEventMessage());
        outputsBox.getChildren().clear();
        event.getOutputs().forEach((id, output) -> {
            Button out = new Button(output.getMessage());
            out.setOnAction(event1 -> {
                switch (output.getContactedType()){
                    case none:
                        return;
                    case Event:
                        loadEvent((Event) output.getContacted());
                        break;
                    case Action:
                        break;
                }
            });
            outputsBox.getChildren().add(out);
        });
    }

    private void show(){
        Stage stage = new Stage();
        Scene scene = new Scene(this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Preview");
        stage.showAndWait();
    }
}
