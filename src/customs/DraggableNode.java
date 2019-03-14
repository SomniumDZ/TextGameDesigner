package customs;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import unnamed.Element;

import java.io.FileInputStream;
import java.io.IOException;

public class DraggableNode<Logic extends Element> extends BorderPane {
    private Logic logic;

    public DraggableNode() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        try {
            loader.load(new FileInputStream("fxmls/Node.fxml"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public Logic getLogic() {
        return logic;
    }
}
