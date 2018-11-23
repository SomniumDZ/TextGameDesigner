package controllers.nodes;

import controllers.MainController;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.io.IOException;

import static main.Main.ew;

public class Output extends GridPane {
    @FXML
    private AnchorPane container;
    @FXML
    private Rectangle connector;
    private Parent parent;
    private double dragOffsetX;
    private double dragOffsetY;


    public Output() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Output.fxml"));
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
        parentProperty().addListener((ChangeListener<? super javafx.scene.Parent>) (observable, oldValue, newValue) -> {
            parent = newValue;
        });


        connector.setOnDragDetected(event -> {
            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
            MainController.setDraggedOut(this);
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();
        });
    }

    public Parent getParentNode(){
        return parent;
    }

    public void setConnectorPosition(double x, double y) {
        Point2D local = ((MainController) Main.getLoader().getController()).getEventsRoot().sceneToLocal(x, y);
        connector.setTranslateX(local.getX()-dragOffsetX-parent.getTranslateX()-getLayoutX()-connector.getLayoutX());
        connector.setTranslateY(local.getY()-dragOffsetY-parent.getTranslateY()-getLayoutY()-connector.getLayoutY());
    }
}
