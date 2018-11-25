package controllers.nodes;

import controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.io.IOException;
import java.util.HashMap;

import static main.Main.ew;

public class Input extends GridPane {

    @FXML
    private Rectangle container;

    private Node parentNode;

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

        parentProperty().addListener((observable, oldValue, newValue) -> parentNode = (Node) newValue);

        container.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));
        container.setOnDragDropped(event -> {
            container.setFill(Color.RED);
            Dragboard db = event.getDragboard();
            if (db.hasString()){
                Rectangle connector = getOutputsMap().get(db.getString()).getConnector();
                getOutputsMap().get(db.getString()).setContacted(this.parentNode);
                connector.translateXProperty().bind(
                        container.translateXProperty()
                                .add(parentNode.translateXProperty())
                                .add(layoutXProperty())
                );
                connector.translateYProperty().bind(container.translateYProperty()
                        .add(parentNode.translateYProperty())
                        .add(layoutYProperty())
                        .add(connector.heightProperty().divide(2))
                );

                connector.setMouseTransparent(false);
            }
            ((MainController)Main.getLoader().getController()).setDraggedOut(null);
            event.consume();
        });
    }

    private HashMap<String, Output> getOutputsMap(){
        return ((MainController)Main.getLoader().getController()).getNodeMap();
    }
}
