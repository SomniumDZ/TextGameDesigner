package controllers.nodes;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static main.Main.ew;

public abstract class Node extends VBox {
    public Node() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/Node.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("FXML error");
        }
    }
}
