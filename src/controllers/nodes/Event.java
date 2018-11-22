package controllers.nodes;

public class Event extends Node {
    private Input message;

    public Event(double x, double y) {
        super(x, y);
        message = new Input();
        getWorkSpace().getChildren().add(message);
    }
}
