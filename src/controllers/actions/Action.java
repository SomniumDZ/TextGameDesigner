package controllers.actions;

import controllers.nodes.Node;

import java.io.IOException;

public abstract class Action extends Node {

    public Action(double x, double y, String fxmlPath) throws IOException {
        super(x, y, fxmlPath);
    }
}
