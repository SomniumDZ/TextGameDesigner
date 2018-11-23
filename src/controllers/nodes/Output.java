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
import javafx.scene.layout.GridPane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.io.IOException;

import static main.Main.ew;

public class Output extends GridPane {
    @FXML
    private CubicCurve link;
    @FXML
    private Rectangle outContainer;
    @FXML
    private Rectangle outConnector;
    private double dragOffsetX;
    private double dragOffsetY;
    Parent parent;


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
        link.controlX1Property().bind(Bindings.add(link.startXProperty(), 100));
        link.controlX2Property().bind(Bindings.add(link.endXProperty(), 100));
        link.controlY1Property().bind(Bindings.add(link.startYProperty(), 100));
        link.controlY2Property().bind(Bindings.add(link.endYProperty(), 100));

        link.startXProperty().bind(outContainer.layoutXProperty());
        link.startYProperty().bind(outContainer.layoutYProperty());
        link.endXProperty().bind(outConnector.layoutXProperty());
        link.endYProperty().bind(outConnector.layoutYProperty());

        outConnector.setOnDragDetected(event -> {
            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
            link.setVisible(true);
            MainController.setDraggedOut(this);
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();
        });
    }


    public void setOutConnectorPosition(double x, double y){
        Point2D local = ((MainController) Main.getLoader().getController()).getEventsRoot().sceneToLocal(x, y);
        outConnector.setTranslateX(
                local.getX()-dragOffsetX-(parent.getTranslateX()+getLayoutX()+outConnector.getLayoutX())
        );
        outConnector.setTranslateY(
                local.getY()-dragOffsetY-(parent.getTranslateY()+getLayoutY()+outConnector.getLayoutY())
        );
    }

    public void setStart(Point2D startPoint) {
        link.setStartX(startPoint.getX());
        link.setStartY(startPoint.getY());
    }

    public void setEnd(Point2D endPoint) {
        link.setEndX(endPoint.getX());
        link.setEndY(endPoint.getY());
    }
}
