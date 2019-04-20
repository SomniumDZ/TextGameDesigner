package controllers.nodes.events;

import controllers.nodes.Node;
import controllers.nodes.Output;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.NumberFormat;

public class Event extends Node {

    public Event(double x, double y, String id) {
        super(x, y, id);
    }

    public Event(double x, double y) {
        super(x, y);
        addOutput(new Output());
        addOutput(new Output());
        addOutput(new Output());
        addOutput(new Output());
        setTitle("EventName");
    }

    public void edit() throws IOException {
        EventEditor editor = new EventEditor(this);
        getInput().setText(editor.getEventMessage());
        clearOutputs();
        editor.getOutputs().forEach((s, output) -> addOutput(output));
    }

    @Override
    public VBox getTools() {
        VBox toolBox = new VBox(5);
        Label titleLabel = new Label("Title");
        TextField titleField = new TextField(getTitle());
        titleField.textProperty().bindBidirectional(getTitleLabel().textProperty());

        Separator separator = new Separator(Orientation.HORIZONTAL);

        Label positionLabel = new Label("Position");
        GridPane positionPane = new GridPane();
        positionPane.add(new Label("X:"), 0, 0);
        positionPane.add(new Label("Y:"), 0, 1);
        TextField xField = new TextField(getTranslateX()+"");
        TextField yField = new TextField(getTranslateY()+"");
        positionPane.add(xField, 1, 0);
        positionPane.add(yField, 1, 1);
        yField.textProperty().bindBidirectional(translateYProperty(), NumberFormat.getInstance());
        xField.textProperty().bindBidirectional(translateXProperty(), NumberFormat.getInstance());

        toolBox.getChildren().addAll(
                titleLabel, titleField,
                separator,
                positionLabel, positionPane
        );
        return toolBox;
    }


    public String getEventMessage() {
        return getInput().getMessage();
    }

    public void setEventMessage(String message){getInput().setText(message);}
}
