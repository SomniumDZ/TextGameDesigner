package controllers.nodes.events;

import controllers.nodes.Input;
import controllers.nodes.Node;
import controllers.nodes.Output;

import java.io.IOException;
import java.util.UUID;

public class Event extends Node {
    private Input input;

    public Event(double x, double y) {
        super(x, y);
        input = new Input();
        getWorkSpace().getChildren().add(input);
        addOutput(UUID.randomUUID().toString(), new Output());
        addOutput(UUID.randomUUID().toString(), new Output());
        addOutput(UUID.randomUUID().toString(), new Output());
        addOutput(UUID.randomUUID().toString(), new Output());
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
        input.setText(editor.getEventMessage());
        clearOutputs();
        editor.getOutputs().forEach(this::addOutput);
    }

    public String getInput() {
        return input.getMessage();
    }

    public void addOutput(String id, Output output){
        getOutputs().put(id, output);
        getWorkSpace().getChildren().add(output);
    }
    public void clearOutputs(){
        getOutputs().forEach((s, output) -> getWorkSpace().getChildren().remove(output));
        getOutputs().clear();
    }
}
