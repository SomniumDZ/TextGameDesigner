package controllers.nodes;

import controllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static main.Main.ew;

public abstract class Node extends VBox {

    @FXML
    private HBox titleBar;
    @FXML
    private VBox workSpace;

    HashMap<String, Output> outputs = new HashMap<>();

    private double dragOffsetX;
    private double dragOffsetY;


    Node(double x, double y) {
        setId(UUID.randomUUID().toString());
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
    }

    private MainController getController() {
        return Main.getLoader().getController();
    }

    @FXML
    private void initialize(){
        buildNodeDrag();
    }

    private void buildNodeDrag() {
        titleBar.setOnDragDetected(event -> {
            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
            MainController.setDraggedNode(this);
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(getId());
            db.setContent(content);
            event.consume();
        });
    }

    public void setTranslatePosition(double x, double y, boolean toLocal){
        if (toLocal) {
            Point2D local = ((MainController)Main.getLoader().getController()).getEventsRoot().sceneToLocal(x, y);
            setTranslateX(local.getX()-dragOffsetX);
            setTranslateY(local.getY()-dragOffsetY);
        }else {
            setTranslateY(y);
            setTranslateX(x);
        }
    }

    public Pane getWorkSpace() {
        return workSpace;
    }
}
