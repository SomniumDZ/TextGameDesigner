package customs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;

import java.io.FileInputStream;
import java.io.IOException;

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
    }

    public String  getOutputText() {
        return outputTextLabel.getText();
    }

    public void setOutputText(String outputText) {
        outputTextLabel.setText(outputText);
    }
}
