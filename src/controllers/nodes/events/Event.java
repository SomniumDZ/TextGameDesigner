package controllers.nodes.events;

import controllers.nodes.Input;
import controllers.nodes.Node;
import controllers.nodes.Output;

import java.io.IOException;

public class Event extends Node {
    private Input input;

    public Event(double x, double y) {
        super(x, y);
        input = new Input();
        getWorkSpace().getChildren().add(input);
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
        input.setText(editor.getEventMessage());
        clearOutputs();
        editor.getOutputs().forEach((s, output) -> addOutput(output));
    }

    public String getInput() {
        return input.getMessage();
    }

    public void addOutput(Output output){
        getOutputs().put(output.getId(), output);
        getWorkSpace().getChildren().add(output);
    }
    public void clearOutputs(){
        getOutputs().forEach((s, output) -> {
            getWorkSpace().getChildren().remove(output);
            output.reset();
        });
        getOutputs().clear();
    }
}
