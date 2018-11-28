package controllers.nodes.events;

import controllers.nodes.Node;
import controllers.nodes.Output;

import java.io.IOException;

public class Event extends Node {

    public Event(double x, double y) {
        super(x, y);
        addOutput(new Output());
        addOutput(new Output());
        addOutput(new Output());
        addOutput(new Output());
        setName("EventName");
        setOnMouseClicked(event -> {
            if (event.getClickCount()==2){
                try {
                    edit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void edit() throws IOException {
        EventEditor editor = new EventEditor(this);
        getInput().setText(editor.getEventMessage());
        clearOutputs();
        editor.getOutputs().forEach((s, output) -> addOutput(output));
    }

    public String getEventMessage() {
        return getInput().getMessage();
    }

    public void setEventMessage(String message){getInput().setText(message);}
}
