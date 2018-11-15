package controllers.events;

import controllers.nodes.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;


public abstract class Event<Visual extends Pane> extends Node<Visual> {
    private Event[] childEvents;

    Event(double x, double y, String fxmlPath, Event[] childEventsContainer) throws IOException {
        super(x,y,fxmlPath);

        childEvents = childEventsContainer;
    }

    public Event[] getChildEvents() {
        return childEvents;
    }
}
