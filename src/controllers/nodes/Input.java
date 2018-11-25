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
                StringBuilder nodeId = new StringBuilder();
                StringBuilder outputId = new StringBuilder();
                for (int i = 0; i < 36; i++) {
                    nodeId.append(db.getString().toCharArray()[i]);
                }
                for (int i = 36; i < 72; i++) {
                    outputId.append(db.getString().toCharArray()[i]);
                }
                System.out.println(nodeId.toString()+ " " + outputId.toString());
                Rectangle connector = getNodesMap().get(nodeId.toString()).getOutputs().get(outputId.toString()).getConnector();
                Node node = getNodesMap().get(nodeId.toString());
                Output output = node.outputs.get(outputId.toString());
                output.setContacted(this.parentNode);
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

    private HashMap<String, Node> getNodesMap(){
        return ((MainController)Main.getLoader().getController()).getNodeMap();
    }
}
