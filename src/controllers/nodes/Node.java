package controllers.nodes;

import controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.UUID;

import static main.Main.ew;

public abstract class Node extends VBox {

    @FXML
    private HBox titleBar;
    @FXML
    private VBox workSpace;
    @FXML
    private Label name;

    LinkedHashMap<String, Output> outputs = new LinkedHashMap<>();
    private Input input = new Input();

    private ContextMenu contextMenu = new ContextMenu();

    private double dragOffsetX;
    private double dragOffsetY;


    public Node(double x, double y) {
        setId(UUID.randomUUID().toString());
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

        setTranslatePosition(x, y, false);

        setOnContextMenuRequested(event -> {
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
            event.consume();
        });
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
            titleBar.setStyle("-fx-background-color: #985d5a");
            getController().getNodeMap().forEach((id, node) -> {
                node.getTitleBar().setStyle("-fx-background-color: lightgray");
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
                    getController().setChosenNode(this);
                }
            }
        });
    }

    public abstract void edit() throws IOException;

    private void buildNodeDrag() {
        titleBar.setOnDragDetected(event -> {
            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
            getController().setDraggedNode(this);
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(getId());
            db.setContent(content);
            event.consume();
        });
    }

    public void setTranslatePosition(double x, double y, boolean toLocal){
        if (toLocal) {
            Point2D local = ((MainController)Main.getLoader().getController()).getSequenceEditorRoot().sceneToLocal(x, y);
            setTranslateX(local.getX()-dragOffsetX);
            setTranslateY(local.getY()-dragOffsetY);
        }else {
            setTranslateY(y);
            setTranslateX(x);
        }
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

    public String getName(){
        return name.getText();
    }

    public HBox getTitleBar() {
        return titleBar;
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }
}
