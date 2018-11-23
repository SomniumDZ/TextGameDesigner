package controllers.nodes;

import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.shape.CubicCurve;

public class NodeLink extends CubicCurve {

    public NodeLink() {
        controlX1Property().bind(Bindings.add(startXProperty(), 100));
        controlX2Property().bind(Bindings.add(endXProperty(), 100));
        controlY1Property().bind(Bindings.add(startYProperty(), 100));
        controlY2Property().bind(Bindings.add(endYProperty(), 100));
    }

    public void setStart(Point2D startPoint) {
        setStartX(startPoint.getX());
        setStartY(startPoint.getY());
    }
}
