package controllers.nodes;

import controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
    @FXML
    private Label message;

    private Node parentNode;

    private HashMap<String, Output> connectedOutputs = new HashMap<>();
    private ContextMenu inputMenu;
    private MenuItem deleteAllOutputs;

    public Input() {
        inputMenu = new ContextMenu();
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
        deleteAllOutputs = new MenuItem("delete all links");
        deleteAllOutputs.setOnAction(event -> {
            connectedOutputs.forEach((id,output) -> output.reset());
            container.setFill(Color.DODGERBLUE);
            inputMenu.getItems().clear();
            connectedOutputs.clear();
        });
        container.setOnContextMenuRequested(event -> {
            inputMenu.show(container, event.getScreenX(), event.getScreenY());
            if (connectedOutputs.size()>1) {
                if (!inputMenu.getItems().contains(deleteAllOutputs)) {
                    inputMenu.getItems().add(deleteAllOutputs);
                }
            }else inputMenu.getItems().remove(deleteAllOutputs);
            event.consume();
        });

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

                connectedOutputs.put(nodeId.toString(), output);
                MenuItem menuItem = new MenuItem("delete link to "+node.getName());
                menuItem.setOnAction(event1 -> {
                    output.reset();
                    connectedOutputs.remove(nodeId.toString());
                    inputMenu.getItems().remove(menuItem);
                    if (connectedOutputs.isEmpty()){
                        container.setFill(Color.DODGERBLUE);
                    }
                });
                inputMenu.getItems().add(menuItem);
                if (inputMenu.getItems().contains(deleteAllOutputs)) {
                    inputMenu.getItems().remove(deleteAllOutputs);
                    inputMenu.getItems().add(deleteAllOutputs);
                }
            }
            ((MainController)Main.getLoader().getController()).setDraggedOut(null);
            event.consume();
        });
    }

    private HashMap<String, Node> getNodesMap(){
        return ((MainController)Main.getLoader().getController()).getNodeMap();
    }

    public HashMap<String, Output> getConnectedOutputs() {
        return connectedOutputs;
    }

    public String getMessage() {
        return message.getText();
    }

    public void setText(String eventMessage) {
        message.setText(eventMessage);
    }
}
