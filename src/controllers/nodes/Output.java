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
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.io.IOException;
import java.util.UUID;

import static main.Main.ew;

public class Output extends GridPane {
    @FXML
    private AnchorPane container;
    @FXML
    private Label message;

    private Rectangle connector;
    private Node parent;
    private CubicCurve curve;
    private Node contacted;
    private int index;

    public Output() {
        this("Output");
    }

    public int getIndex() {
        return index;
    }

    public enum ContactedType {
        Event, Action, none
    }

    private ContactedType contactedType = ContactedType.none;

    private final DoubleProperty controlDirectionX1 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionY1 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionX2 = new SimpleDoubleProperty();
    private final DoubleProperty controlDirectionY2 = new SimpleDoubleProperty();


    public Output(String id, String message) {
        setId(id);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Output.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            this.message.setText(message);
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("FXML read error");
        }
    }

    public Output(String message) {
        this(UUID.randomUUID().toString(), message);
    }

    @FXML
    public void initialize(){
        parentProperty().addListener((ChangeListener<? super javafx.scene.Parent>) (observable, oldValue, newValue) -> {
            parent = (Node) newValue;
            index = parent.getOutputs().size();
        });

        container.setOnDragDetected(event -> {
            reset();
            getController().setDraggedOut(this);
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(parent.getId()+getId());
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

        curve.setMouseTransparent(true);
        connector.setMouseTransparent(true);

        return curve;
    }

    public void reset() {
        getEventRoot().getChildren().removeAll(curve, connector);
        connector = null;
        curve = null;
    }

    private Pane getEventRoot() {
        return ((MainController) Main.getLoader().getController()).getChosenSequenceEditorRoot();
    }

    public Parent getParentNode(){
        return parent;
    }

    public void setConnectorPosition(double x, double y) {
        Point2D local = getEventRoot().sceneToLocal(x, y);
        connector.setTranslateX(local.getX()-connector.getWidth()/2);
        connector.setTranslateY(local.getY()-connector.getHeight()/2);
    }

    public Node getContacted() {
        return contacted;
    }

    public void setContacted(Node contacted) {
        this.contacted = contacted;
    }

    public Rectangle getConnector() {
        return connector;
    }

    public AnchorPane getContainer() {
        return container;
    }

    private MainController getController() {
        return Main.getLoader().getController();
    }

    public String getMessage() {
        return message.getText();
    }

    public ContactedType getContactedType() {
        return contactedType;
    }

    public void setContactedType(ContactedType contactedType) {
        this.contactedType = contactedType;
    }

    public void setText(String text) {
        message.setText(text);
    }

    public void setCurve(CubicCurve curve){
        this.curve = curve;
    }
}
