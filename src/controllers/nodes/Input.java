package controllers.nodes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

import static main.Main.ew;

public class Input extends GridPane {

    @FXML
    private Rectangle container;

    public Input() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Input.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("FXML read error");
        }
    }

    public void initialize(){
        container.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));
        container.setOnDragDropped(event -> {
            container.setFill(Color.RED);
        });
    }
}
