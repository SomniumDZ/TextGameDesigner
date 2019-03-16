package customs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Output extends HBox {
    @FXML
    Circle outputTrigger;
    @FXML
    Circle outputDraggableElement;
    @FXML
    CubicCurve linkCurve;
    @FXML
    Label outputTextLabel;

    public Output() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load(new FileInputStream("fxmls/Output.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AtomicReference<Double> deltaX = new AtomicReference<>((double) 0);
        AtomicReference<Double> deltaY = new AtomicReference<>((double) 0);
        outputDraggableElement.setMouseTransparent(true);
        outputTrigger.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            deltaX.set(outputTrigger.getTranslateX() - mouseEvent.getSceneX());
            deltaY.set(outputTrigger.getTranslateY() - mouseEvent.getSceneY());
        });
        outputTrigger.setOnMouseDragged(mouseEvent -> {
            outputDraggableElement.setTranslateX(mouseEvent.getSceneX() + deltaX.get());
            outputDraggableElement.setTranslateY(mouseEvent.getSceneY() + deltaY.get());
        });

        outputTrigger.setOnDragDetected(event -> {
            startDragAndDrop(TransferMode.ANY);
            System.out.println("detected");
        });
    }

    public String  getOutputText() {
        return outputTextLabel.getText();
    }

    public void setOutputText(String outputText) {
        outputTextLabel.setText(outputText);
    }
}
