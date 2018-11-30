package controllers.nodes;

import java.io.IOException;

public class EmptyNode extends Node {
    public EmptyNode(double x, double y) {
        super(x,y);
    }

    @Override
    public void edit() throws IOException {
    }
}
