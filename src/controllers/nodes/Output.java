package controllers.nodes;

import controllers.MainController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static main.Main.ew;

public class Output extends GridPane {
    @FXML
    private AnchorPane container;
    private Rectangle connector;
    // TODO: 24.11.2018 Вывести коннэктор за ноду
    private Parent parent;
    private double dragOffsetX;
    private double dragOffsetY;
    private CubicCurve curve;
    private Node contacted;

    private final DoubleProperty controlDirectionX1 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionY1 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionX2 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionY2 = new SimpleDoubleProperty();


    public Output() {
        setId(UUID.randomUUID().toString());
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
        getOutputMap().put(getId(), this);

        container.setOnDragDetected(event -> {
            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
            MainController.setDraggedOut(this);
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(getId());
            db.setContent(content);

            if (curve==null) {
                curve = spawnNewConnectionCurve(container);
                getEventRoot().getChildren().addAll(curve, connector);
            }

            event.consume();
        });
    }

    public CubicCurve spawnNewConnectionCurve(AnchorPane container){
        CubicCurve curve = new CubicCurve();
        this.connector = new Rectangle();

        controlDirectionX1.bind(new When(
                curve.startXProperty().greaterThan(curve.endXProperty()))
                .then(-1.0).otherwise(1.0)
        );

        controlDirectionX2.bind(new When (
                curve.startXProperty().greaterThan(curve.endXProperty()))
                .then(1.0).otherwise(-1.0)
        );

        curve.controlX1Property().bind(
                Bindings.add(
                        curve.startXProperty(), controlDirectionX1.multiply(100)
                )
        );

        curve.controlX2Property().bind(
                Bindings.add(
                        curve.endXProperty(), controlDirectionX2.multiply(100)
                )
        );

        curve.controlY1Property().bind(
                Bindings.add(
                        curve.startYProperty(), controlDirectionY1.multiply(50)
                )
        );

        curve.controlY2Property().bind(
                Bindings.add(
                        curve.endYProperty(), controlDirectionY2.multiply(50)
                )
        );

        curve.startXProperty().bind(
                parent.translateXProperty()
                        .add(layoutXProperty())
                        .add(container.layoutXProperty())
                        .add(container.widthProperty().divide(2))
        );
        curve.startYProperty().bind(
                parent.translateYProperty()
                        .add(layoutYProperty())
                        .add(container.heightProperty().divide(2))
        );

        curve.endXProperty().bind(connector.translateXProperty());
        curve.endYProperty().bind(connector.translateYProperty()
                .add(connector.heightProperty().divide(2))
        );

        curve.setStyle(""+
                "-fx-fill: transparent;"+
                "-fx-stroke-width: 2;" +
                "-fx-stroke: #7c7c7c;"

        );

        connector.setStyle("" +
                "-fx-arc-width: 5.0;" +
                "-fx-arc-height: 5.0;" +
                "-fx-fill: #10cc00;" +
                "-fx-stroke: black;" +
                "-fx-stroke-type: inside;"
        );

        connector.setWidth(20);
        connector.setHeight(15);


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
        connector.setTranslateX(local.getX()-dragOffsetX);
        connector.setTranslateY(local.getY()-dragOffsetY);
    }

    public Node getContacted() {
        return contacted;
    }

    public void setContacted(Node contacted) {
        this.contacted = contacted;
    }

    private HashMap<String, Output> getOutputMap(){
        return ((MainController)Main.getLoader().getController()).getNodeMap();
    }

    public Rectangle getConnector() {
        return connector;
    }

    public void reset() {
        getEventRoot().getChildren().removeAll(curve, connector);
        connector = null;
        curve = null;
    }
}
