package controllers.nodes;

import controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Main;
import main.service.LambdaResistantReference;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.UUID;

import static main.Main.ew;

public abstract class Node extends VBox {

    @FXML
    private HBox header;
    @FXML
    private VBox workSpace;
    @FXML
    private Label title;

    LinkedHashMap<String, Output> outputs = new LinkedHashMap<>();
    private Input input = new Input();

    private ContextMenu contextMenu = new ContextMenu();

    private double dragOffsetX;
    private double dragOffsetY;


    public Node(double x, double y, String id) {
        setId(id);
        getController().getNodeMap().put(getId(), this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Node.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("FXML error");
        }

        setTranslatePosition(x, y);

        setOnContextMenuRequested(event -> {
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
            event.consume();
        });
    }

    public Node(Double x, Double y) {
        this(x, y, UUID.randomUUID().toString());
    }

    private MainController getController() {
        return Main.getLoader().getController();
    }

    @FXML
    private void initialize(){
        buildNodeDrag();
        getWorkSpace().getChildren().add(input);
        MenuItem markInitial = new MenuItem("Mark as initial");
        markInitial.setOnAction(event -> {
            getController().setInitialNode(this);
            header.setStyle("-fx-background-color: #985d5a");
            getController().getNodeMap().forEach((id, node) -> {
                node.getHeader().setStyle("-fx-background-color: lightgray");
            });
            event.consume();
        });
        contextMenu.getItems().add(markInitial);

        setOnMouseClicked(event -> {
            if (event.getClickCount()>=2){
                try {
                    edit();
                } catch (IOException e) {
                    e.printStackTrace();
                    ew.throwError("Editor error");
                }
            }else {
                if (event.getButton()!= MouseButton.SECONDARY) {
                    if (getController().getSelectedNode() != this) {
                        getController().setSelectedNode(this);
                    }
                    event.consume();
                }
            }
        });
    }

    public abstract void edit() throws IOException;

    private void buildNodeDrag() {
        LambdaResistantReference<Double> deltaX = new LambdaResistantReference<>((double) 0);
        LambdaResistantReference<Double> deltaY = new LambdaResistantReference<>((double) 0);
        header.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            deltaX.set(getTranslateX() - mouseEvent.getSceneX());
            deltaY.set(getTranslateY() - mouseEvent.getSceneY());
        });
        header.setOnMouseDragged(mouseEvent -> {
            setTranslateX(mouseEvent.getSceneX() + deltaX.get());
            setTranslateY(mouseEvent.getSceneY() + deltaY.get());
        });
    }

    public void setTranslatePosition(double x, double y){
        setTranslateX(x);
        setTranslateY(y);
    }

    public void setTranslatePosition(Point2D position){
        setTranslateX(position.getX());
        setTranslateY(position.getY());
    }

    public void addOutput(Output output){
        getOutputs().put(output.getId(), output);
        getWorkSpace().getChildren().add(output);
    }
    public void clearOutputs(){
        getOutputs().forEach((s, output) -> {
            getWorkSpace().getChildren().remove(output);
            output.reset();
        });
        getOutputs().clear();
    }
    public LinkedHashMap<String, Output> getOutputs() {
        return outputs;
    }

    public Input getInput() {
        return input;
    }

    public Pane getWorkSpace() {
        return workSpace;
    }

    public String getTitle(){
        return title.getText();
    }

    public HBox getHeader() {
        return header;
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    protected Label getTitleLabel() {
        return title;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public abstract VBox getTools();
}
