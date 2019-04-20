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
import java.util.ArrayList;
import java.util.HashMap;

import static main.Main.ew;

public class Input extends GridPane {

    @FXML
    private Rectangle container;
    @FXML
    private Label message;

    private Node parentNode;

    private ArrayList<Output> connectedOutputs = new ArrayList<>();
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

    @FXML
    public void initialize(){
        parentProperty().addListener((observable, oldValue, newValue) -> parentNode = (Node) newValue);
        deleteAllOutputs = new MenuItem("delete all links");
        deleteAllOutputs.setOnAction(event -> {
            connectedOutputs.forEach(Output::reset);
            container.setFill(Color.DODGERBLUE);
            inputMenu.getItems().clear();
            connectedOutputs.clear();
        });
        container.setOnContextMenuRequested(event -> {
            inputMenu.show(container, event.getScreenX(), event.getScreenY());
            event.consume();
        });

        container.setOnDragOver(event -> event.acceptTransferModes(TransferMode.ANY));
        container.setOnDragDropped(event -> {
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
                connect(nodeId.toString(), outputId.toString());
            }
            ((MainController)Main.getLoader().getController()).getChosenLocation().setDraggedOut(null);
            event.consume();
        });
    }

    public void connect(String  nodeId, String  outputId){
        container.setFill(Color.RED);
        System.out.println(nodeId + " " + outputId);
        Rectangle connector = getNodesMap().get(nodeId).getOutputs().get(outputId).getConnector();
        Node node = getNodesMap().get(nodeId);
        Output output = node.outputs.get(outputId);
        output.setContacted(this.parentNode);
        System.out.println(parentNode.getClass().getName());
        switch (this.parentNode.getClass().getSimpleName()){
            case "Event":
                output.setContactedType(Output.ContactedType.Event);
                break;
            default:
                output.setContactedType(Output.ContactedType.none);
        }

        connector.setTranslateX(container.getTranslateX() + parentNode.getTranslateX() + getLayoutX());
        connector.translateXProperty().bind(
                container.translateXProperty()
                        .add(parentNode.translateXProperty())
                        .add(layoutXProperty())
        );

        connector.setTranslateY(container.getTranslateY() + parentNode.getTranslateY() + getLayoutY());
        connector.translateYProperty().bind(container.translateYProperty()
                .add(parentNode.translateYProperty())
                .add(layoutYProperty())
                .add(connector.heightProperty().divide(2))
        );

        connectedOutputs.add(output);

        MenuItem menuItem = new MenuItem("delete link to "+ node.getTitle() + "("+output.getIndex()+")");
        node.getTitleLabel().textProperty().addListener((observable, oldValue, newValue) -> {
            menuItem.setText("delete link to "+ newValue + "("+output.getIndex()+")");
        });

        menuItem.setOnAction(event1 -> {
            output.reset();
            connectedOutputs.remove(output);
            if (connectedOutputs.size()<1){
                container.setFill(Color.DODGERBLUE);
            }
            inputMenu.getItems().remove(menuItem);

        });
        inputMenu.getItems().add(menuItem);
        if (inputMenu.getItems().contains(deleteAllOutputs)) {
            inputMenu.getItems().remove(deleteAllOutputs);
            inputMenu.getItems().add(deleteAllOutputs);
        }
        if (inputMenu.getItems().size()>1){
            if (!inputMenu.getItems().contains(deleteAllOutputs)){
                inputMenu.getItems().add(deleteAllOutputs);
            }
        }else inputMenu.getItems().remove(deleteAllOutputs);
    }

    private HashMap<String, Node> getNodesMap(){
        return ((MainController)Main.getLoader().getController()).getNodeMap();
    }

    public ArrayList<Output> getConnectedOutputs() {
        return connectedOutputs;
    }

    public String getMessage() {
        return message.getText();
    }

    public void setText(String eventMessage) {
        message.setText(eventMessage);
    }
}
