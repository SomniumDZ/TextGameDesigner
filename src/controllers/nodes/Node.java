package controllers.nodes;

import controllers.events.VisualController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class Node <Visual extends Pane, VC extends VisualController>{
    private Visual visual;
    private FXMLLoader visualLoader;

    public Node(double x, double y, String fxmlPath) throws IOException {
        visualLoader = new FXMLLoader();
        this.visual = visualLoader.load(new FileInputStream(fxmlPath));
        visual.setStyle("-fx-background-color: #adadad;"
//               +"-fx-border-image-source: url(Border.png);" +
//                "-fx-border-image-slice: 6px;" +
//                "-fx-border-image-insets: 0px;" +
//                "-fx-border-image-repeat: repeat;" +
//                "-fx-border-image-width: 6px"
        );
        visual.setTranslateX(x);
        visual.setTranslateY(y);
    }

    public VC getVisualController(){
        return visualLoader.getController();
    }

    public Visual getVisual() {
        return visual;
    }

}
