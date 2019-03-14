package controllers;

import javafx.beans.DefaultProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

@DefaultProperty("elements")
public class DraggableNodeController {
    @FXML
    Circle input;
    @FXML
    VBox elements;


}
