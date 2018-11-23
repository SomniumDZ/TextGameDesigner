package controllers.nodes;

import controllers.MainController;
import javafx.beans.binding.Bindings;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
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
    private CubicCurve curve;


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

            if (curve==null) {
                curve = spawnNewConnectionCurve(container, connector);
                getEventRoot().getChildren().add(curve);
                curve.toBack();
            }

            event.consume();
        });
    }

    public CubicCurve spawnNewConnectionCurve(AnchorPane container, Rectangle connector){
        CubicCurve curve = new CubicCurve();

        curve.controlX1Property().bind(Bindings.add(curve.startXProperty(), 100));
        curve.controlX2Property().bind(Bindings.add(curve.endXProperty(), 100));
        curve.controlY1Property().bind(Bindings.add(curve.startYProperty(), 100));
        curve.controlY2Property().bind(Bindings.add(curve.endYProperty(), 100));

        curve.setTranslateX(parent.getTranslateX()+getLayoutX()+container.getLayoutX()+container.getWidth()/2);
        curve.setTranslateY(parent.getTranslateY()+getLayoutY()+container.getHeight()/2);

        curve.startXProperty().bind(container.translateXProperty());
        curve.startYProperty().bind(container.translateYProperty());

        curve.endXProperty().bind(connector.translateXProperty());
        curve.endYProperty().bind(connector.translateYProperty());

        curve.setFill(Color.rgb(0,0,0,0));
        curve.setStroke(Color.rgb(0,0,0,1));


        return curve;
    }

    private AnchorPane getEventRoot() {
        return ((MainController) Main.getLoader().getController()).getEventsRoot();
    }

    public Parent getParentNode(){
        return parent;
    }

    public void setConnectorPosition(double x, double y) {
        Point2D local = getEventRoot().sceneToLocal(x, y);
        connector.setTranslateX(local.getX()-dragOffsetX-parent.getTranslateX()-getLayoutX()-connector.getLayoutX());
        connector.setTranslateY(local.getY()-dragOffsetY-parent.getTranslateY()-getLayoutY()-connector.getLayoutY());
    }
}
