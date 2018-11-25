package controllers.nodes.events;

import controllers.nodes.Input;
import controllers.nodes.Node;
import controllers.nodes.Output;

import java.util.ArrayList;

public class Event extends Node {
    private Input message;
    private ArrayList<Output> outputs;

    public Event(double x, double y) {
        super(x, y);
        message = new Input();
        outputs = new ArrayList<>();
        Output output = new Output();
        outputs.add(output);
        getWorkSpace().getChildren().add(message);
        getWorkSpace().getChildren().add(output);
        setName("EventName");
    }
}
